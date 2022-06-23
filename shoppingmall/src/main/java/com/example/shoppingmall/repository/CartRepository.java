package com.example.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByMemberId(Long memberId);
}
