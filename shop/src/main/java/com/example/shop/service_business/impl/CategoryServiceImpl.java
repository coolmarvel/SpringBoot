package com.example.shop.service_business.impl;

import com.example.shop.model.Category;
import com.example.shop.model.exeptions.CategoryNotFoundException;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.service_business.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Category cat = this.findById(id);
        cat.setName(category.getName());
        cat.setProducts(category.getProducts());
        return this.categoryRepository.save(cat);
    }

    @Override
    public Category updateName(Long id, String name) {
        Category cat = this.findById(id);
        cat.setName(name);
        return this.categoryRepository.save(cat);
    }

    @Override
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findByParentId(Long id) {
        return this.categoryRepository.findAllByParentId(id);
    }

    @Override
    public List<Category> findAllParents() {
        return this.categoryRepository.findAllByParentIsNull();
    }

    @Override
    public List<Category> findAllSubcategoriesNotNull() {
        return this.categoryRepository.findAllBySubcategoriesIsNotNull();
    }

    @Override
    public List<Category> findAllSubcategories() {
        return this.categoryRepository.findAllBySubcategoriesIsNull();
    }

    @Override
    public List<Category> findAllById(Long id) {
        return this.categoryRepository.findAllById(id);
    }
}
