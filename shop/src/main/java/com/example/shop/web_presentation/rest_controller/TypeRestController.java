package com.example.shop.web_presentation.rest_controller;

import com.example.shop.model.Type;
import com.example.shop.service_business.TypeService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/types")
public class TypeRestController {

    private final TypeService typeService;

    public TypeRestController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public List<Type> findAll(@RequestParam(required = false) String name) {
        return this.typeService.findAll();
    }

    @GetMapping("/{id}")
    public Type findById(@PathVariable Long id) {
        return this.typeService.findById(id);
    }

    @PostMapping
    public Type save(@Valid Type type) {
        return this.typeService.save(type);
    }

    @PutMapping("/{id}")
    public Type update(@PathVariable Long id, @Valid Type type) {
        return this.typeService.update(id, type);
    }

    @PatchMapping("/{id}")
    public Type updateName(@PathVariable Long id, @RequestParam String name) {
        return this.typeService.updateName(id, name);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.typeService.deleteById(id);
    }
}
