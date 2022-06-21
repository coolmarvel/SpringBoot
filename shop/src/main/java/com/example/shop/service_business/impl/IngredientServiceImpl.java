package com.example.shop.service_business.impl;

import com.example.shop.model.Ingredient;
import com.example.shop.model.exeptions.IngredientNotFoundException;
import com.example.shop.repository.IngredientRepository;
import com.example.shop.service_business.IngredientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findAll() {
        return this.ingredientRepository.findAll();
    }

    @Override
    public Ingredient findById(Long id) {
        return this.ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException(id));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return this.ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient update(Long id, Ingredient ingredient) {
        Ingredient ingredient1 = this.findById(id);
        ingredient1.setName(ingredient.getName());
        return this.ingredientRepository.save(ingredient1);
    }

    @Override
    public Ingredient updateName(Long id, String name) {
        Ingredient ingredient1 = this.findById(id);
        ingredient1.setName(name);
        return this.ingredientRepository.save(ingredient1);
    }

    @Override
    public void deleteById(Long id) {
        this.ingredientRepository.deleteById(id);
    }
}
