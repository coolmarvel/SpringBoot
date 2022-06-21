package com.example.shop.service_business;

import com.example.shop.model.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAll();

    Ingredient findById(Long id);

    Ingredient save(Ingredient ingredient);

    Ingredient update(Long id, Ingredient ingredient);

    Ingredient updateName(Long id, String name);

    void deleteById(Long id);
}
