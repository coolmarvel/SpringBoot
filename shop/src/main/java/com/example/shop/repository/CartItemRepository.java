package com.example.shop.repository;

import com.example.shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllById(Long id);

    List<CartItem> findAllByShoppingCartId(@Param("id") Long id);

    List<CartItem> findAllByProductId(@Param("id") Long id);

    CartItem findByShoppingCartId(@Param("id") Long id);

    CartItem findByProductId(@Param("id") Long id);

    CartItem findByProductIdAndShoppingCartId(@Param("id") Long productId, @Param("id") Long cartId);
}
