package com.example.shop.web_presentation.rest_controller;

import com.example.shop.model.Ingredient;
import com.example.shop.service_business.IngredientService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientRestController {
    private final IngredientService ingredientService;

    public IngredientRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<Ingredient> findAll(@RequestParam(required = false) String name) {
        return this.ingredientService.findAll();
    }

    @GetMapping("/{id}")
    public Ingredient findById(@PathVariable Long id) {
        return this.ingredientService.findById(id);
    }

    @PostMapping
    public Ingredient save(@Valid Ingredient type) {
        return this.ingredientService.save(type);
    }

    @PutMapping("/{id}")
    public Ingredient update(@PathVariable Long id, @Valid Ingredient type) {
        return this.ingredientService.update(id, type);
    }

    @PatchMapping("/{id}")
    public Ingredient updateName(@PathVariable Long id, @RequestParam String name) {
        return this.ingredientService.updateName(id, name);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.ingredientService.deleteById(id);
    }
}
