package com.example.shop.service_business;

import com.example.shop.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    Category update(Long id, Category category);

    Category updateName(Long id, String name);

    void deleteById(Long id);

    List<Category> findByParentId(Long id);

    List<Category> findAllParents();

    List<Category> findAllSubcategoriesNotNull();

    List<Category> findAllSubcategories();

    List<Category> findAllById(Long id);

}
