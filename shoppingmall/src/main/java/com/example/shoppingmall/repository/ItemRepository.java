package com.example.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
}
