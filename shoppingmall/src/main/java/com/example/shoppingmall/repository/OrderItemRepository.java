package com.example.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
