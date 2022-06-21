package com.example.shop.repository;


import com.example.shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllBySubcategoriesIsNotNull();
    List<Category> findAllBySubcategoriesIsNull();
    List<Category> findAllById(@Param("id") Long id);
    List<Category> findAllByParentIsNull();
    List<Category> findAllByParentId(@Param("id") Long id);

}
