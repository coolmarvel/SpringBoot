package com.example.shop.service_business.impl;

import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.model.ShoppingCart;
import com.example.shop.model.User;
import com.example.shop.model.dto.ChargeRequest;
import com.example.shop.model.enumerations.CartStatus;
import com.example.shop.model.exeptions.*;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.repository.ShoppingCartRepository;
import com.example.shop.service_business.PaymentService;
import com.example.shop.service_business.ProductService;
import com.example.shop.service_business.ShoppingCartService;
import com.example.shop.service_business.UserService;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final UserService userService;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    public ShoppingCartServiceImpl(UserService userService,
            ProductService productService, PaymentService paymentService, ShoppingCartRepository shoppingCartRepository,
            CartItemRepository cartItemRepository) {
        this.userService = userService;
        this.productService = productService;
        this.paymentService = paymentService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public ShoppingCart findActiveShoppingCartByUsername(String userId) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));
    }

    @Override
    public List<ShoppingCart> findAllByUsername(String userId) {
        return this.shoppingCartRepository.findAllByUserUsername(userId);
    }

    @Override
    public ShoppingCart createNewShoppingCart(String userId) {
        User user = this.userService.findByUsername(userId);
        if (this.shoppingCartRepository.existsByUserUsernameAndStatus(
                user.getUsername(),
                CartStatus.CREATED)) {
            throw new ShoppingCartIsAlreadyCreated(userId);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart addProductToShoppingCart(String userId, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        Product product = this.productService.findById(productId);
        List<CartItem> cartItems = this.cartItemRepository.findAllByShoppingCartId(shoppingCart.getId());
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId().equals(productId)
                    && cartItem.getShoppingCart().getId().equals(shoppingCart.getId())) {
                Integer quantity = cartItem.getQuantity();
                quantity += 1;
                cartItem.setQuantity(quantity);
                this.cartItemRepository.save(cartItem);
                return this.shoppingCartRepository.save(shoppingCart);
            }
        }
        CartItem cartItem1 = new CartItem(null, product, shoppingCart, 1);
        this.cartItemRepository.save(cartItem1);
        shoppingCart.getCartItems().add(cartItem1);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart removeProductFromShoppingCart(String userId, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        List<CartItem> cartItems = this.cartItemRepository.findAllByShoppingCartId(shoppingCart.getId());
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId().equals(productId)
                    && cartItem.getShoppingCart().getId().equals(shoppingCart.getId())) {
                if (cartItem.getQuantity() > 1) {
                    Integer quantity = cartItem.getQuantity();
                    quantity -= 1;
                    cartItem.setQuantity(quantity);
                    this.cartItemRepository.save(cartItem);
                    return this.shoppingCartRepository.save(shoppingCart);
                }
                if (cartItem.getQuantity() == 1) {
                    cartItem.setQuantity(0);
                    cartItem.setProduct(null);
                    cartItem.setShoppingCart(null);
                    this.cartItemRepository.save(cartItem);
                    return this.shoppingCartRepository.save(shoppingCart);
                }
            }
        }

        shoppingCart.setCartItems(
                shoppingCart.getCartItems()
                        .stream()
                        .filter(cartItem -> !cartItem.getProduct().getId().equals(productId))
                        .collect(Collectors.toList()));
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String userId) {
        return this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    User applicationUser = this.userService.findByUsername(userId);
                    shoppingCart.setUser(applicationUser);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart cancelActiveShoppingCart(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));
        shoppingCart.setStatus(CartStatus.CANCELED);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));

        List<CartItem> cartItems = shoppingCart.getCartItems();
        float price = 0;

        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getQuantity() <= 0) {
                throw new ProductOutOfStockException(cartItem.getProduct().getName());
            }
            cartItem.getProduct().setQuantity(cartItem.getProduct().getQuantity() - cartItem.getQuantity());
            price += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        Charge charge = null;
        try {
            charge = this.paymentService.pay(chargeRequest);
        } catch (CardException | APIException | AuthenticationException | APIConnectionException
                | InvalidRequestException e) {
            throw new TransactionFailedException(userId, e.getMessage());
        }

        shoppingCart.setCartItems(cartItems);
        shoppingCart.setStatus(CartStatus.FINISHED);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public Integer getProductAmount(String id) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(id, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(id));

        List<CartItem> cartItems = shoppingCart.getCartItems();
        Integer amount = 0;
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getQuantity() <= 0) {
                amount += 0;
            }
            amount += cartItem.getQuantity();
        }
        return amount;
    }

    @Override
    public Integer getProductQty(String id) {
        boolean isactive = this.shoppingCartRepository.existsByUserUsernameAndStatus(id, CartStatus.CREATED);

        Integer amount = 0;
        if (isactive) {
            ShoppingCart shoppingCart = this.shoppingCartRepository
                    .findByUserUsernameAndStatus(id, CartStatus.CREATED)
                    .orElseThrow(() -> new ShoppingCartIsNotActiveException(id));
            List<CartItem> cartItems = shoppingCart.getCartItems();
            for (CartItem cartItem : cartItems) {
                if (cartItem.getProduct().getQuantity() <= 0) {
                    amount += 0;
                } else
                    amount += cartItem.getQuantity();
            }
        }
        return amount;
    }
}
