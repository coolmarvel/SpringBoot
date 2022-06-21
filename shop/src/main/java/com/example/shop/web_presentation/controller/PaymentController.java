package com.example.shop.web_presentation.controller;

import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.model.ShoppingCart;
import com.example.shop.model.dto.ChargeRequest;
import com.example.shop.service_business.AuthService;
import com.example.shop.service_business.ShoppingCartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Value("${STRIPE_P_KEY}")
    private String publicKey;

    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;

    public PaymentController(ShoppingCartService shoppingCartService, AuthService authService) {
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }

    @GetMapping("/charge")
    public String getCheckoutPage(Model model) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService
                    .findActiveShoppingCartByUsername(this.authService.getCurrentUserId());
            Integer quantity = this.shoppingCartService.getProductAmount(this.authService.getCurrentUserId());
            float amount = 0;
            for (CartItem cartItem : shoppingCart.getCartItems()) {
                amount += cartItem.getProduct().getPrice() * cartItem.getQuantity();
            }
            model.addAttribute("productsquantity", quantity);
            model.addAttribute("shoppingCart", shoppingCart);
            model.addAttribute("currency", "eur");
            model.addAttribute("amount", (int) amount * 100);
            model.addAttribute("stripePublicKey", this.publicKey);
            return "cart";
        } catch (RuntimeException ex) {
            return "redirect:/products?error=" + ex.getLocalizedMessage();
        }
    }

    @PostMapping("/charge")
    public String checkout(ChargeRequest chargeRequest, Model model) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService
                    .checkoutShoppingCart(this.authService.getCurrentUserId(), chargeRequest);
            return "redirect:/products?message=Successful Payment";
        } catch (RuntimeException ex) {
            return "redirect:/payments/charge?error=" + ex.getLocalizedMessage();
        }
    }
}
