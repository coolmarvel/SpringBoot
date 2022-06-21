package com.example.shop.web_presentation.rest_controller;

import com.example.shop.model.Brand;
import com.example.shop.service_business.BrandService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

    private final BrandService brandService;

    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public List<Brand> findAll(@RequestParam(required = false) String name) {
        if (name == null) {
            return this.brandService.findAll();
        } else {
            return this.brandService.findAllByName(name);
        }
    }

    @GetMapping("/by-name")
    public List<Brand> findAllByName(@RequestParam String name) {
        return this.brandService.findAllByName(name);
    }

    @GetMapping("/{id}")
    public Brand findById(@PathVariable Long id) {
        return this.brandService.findById(id);
    }

    @PostMapping
    public Brand save(@Valid Brand brand) {
        return this.brandService.save(brand);
    }

    @PutMapping("/{id}")
    public Brand update(@PathVariable Long id, @Valid Brand brand) {
        return this.brandService.update(id, brand);
    }

    @PatchMapping("/{id}")
    public Brand updateName(@PathVariable Long id, @RequestParam String name) {
        return this.brandService.updateName(id, name);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.brandService.deleteById(id);
    }

}
