package com.example.shop.web_presentation.controller;

import com.example.shop.model.Brand;
import com.example.shop.model.Category;
import com.example.shop.model.Product;
import com.example.shop.model.Type;
import com.example.shop.service_business.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;
    private ProductService productService;
    private BrandService brandService;
    private ShoppingCartService shoppingCartService;
    private AuthService authService;
    private TypeService typeService;

    public CategoryController(CategoryService categoryService,
            ProductService productService,
            BrandService brandService, ShoppingCartService shoppingCartService, AuthService authService,
            TypeService typeService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.brandService = brandService;
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
        this.typeService = typeService;
    }

    @GetMapping
    public String getCategoryPage(Model model) {
        List<Category> categories = this.categoryService.findAllParents();
        Integer quantity = this.shoppingCartService.getProductQty(this.authService.getCurrentUserId());
        List<Type> types = this.typeService.findAll();
        model.addAttribute("productsquantity", quantity);
        model.addAttribute("categories", categories);
        model.addAttribute("types", types);
        return "categories";
    }

    @GetMapping("/category/{id}")

    public String getCategoriesPage(Model model, @PathVariable Long id) {
        List<Category> categories = this.categoryService.findAllSubcategoriesNotNull();
        for (Category cat : categories) {
            if (cat.getId().equals(id)) {
                List<Category> subcategories = this.categoryService.findByParentId(id);
                Integer quantity = this.shoppingCartService.getProductQty(this.authService.getCurrentUserId());
                model.addAttribute("productsquantity", quantity);
                model.addAttribute("categories", subcategories);
                return "categories";
            }
        }

        List<Product> products = this.productService.findAllByCategoryId(id);
        Category subcategory = this.categoryService.findById(id);
        Category category = subcategory.getParent();
        List<Category> subcategories = this.categoryService.findByParentId(category.getId());
        List<Brand> brands = this.brandService.findAll();
        Integer quantity = this.shoppingCartService.getProductQty(this.authService.getCurrentUserId());
        List<Type> types = this.typeService.findAll();
        model.addAttribute("productsquantity", quantity);
        model.addAttribute("products", products);
        model.addAttribute("categories", subcategories);
        model.addAttribute("brands", brands);
        model.addAttribute("types", types);
        return "index";

    }

    @GetMapping("/types/{id}")
    public String getTypesPage(Model model, @PathVariable Long id) {
        List<Product> products = this.productService.findAllByTypeId(id);
        List<Category> categories = this.categoryService.findAllParents();
        List<Brand> brands = this.brandService.findAll();
        Integer quantity = this.shoppingCartService.getProductQty(this.authService.getCurrentUserId());
        List<Type> types = this.typeService.findAll();
        model.addAttribute("productsquantity", quantity);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("types", types);
        return "index";
    }
}
