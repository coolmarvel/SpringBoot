package com.example.shop.service_business;

import com.example.shop.model.ShoppingCart;
import com.example.shop.model.dto.ChargeRequest;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart findActiveShoppingCartByUsername(String userId);

    List<ShoppingCart> findAllByUsername(String userId);

    ShoppingCart createNewShoppingCart(String userId);

    ShoppingCart addProductToShoppingCart(String userId,
            Long productId);

    ShoppingCart removeProductFromShoppingCart(String userId,
            Long productId);

    ShoppingCart getActiveShoppingCart(String userId);

    ShoppingCart cancelActiveShoppingCart(String userId);

    ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest);

    Integer getProductAmount(String id);

    Integer getProductQty(String id);

}
