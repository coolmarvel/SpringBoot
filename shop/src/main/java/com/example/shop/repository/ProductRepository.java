package com.example.shop.repository;


import com.example.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryId(@Param("id") Long id);
    List<Product> findAllByBrandId(@Param("id") Long id);
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByPriceDesc();
    long countAllByPriceGreaterThan(@Param("price") Float price);
    List<Product> findAllByPriceGreaterThan(@Param("price") Float price);
    List<Product> findAllByNameLikeAndBrandIdOrderByPriceDesc(String name, Long brandId);
    List<Product> findAllByTypeId(@Param("id") Long id);
}
