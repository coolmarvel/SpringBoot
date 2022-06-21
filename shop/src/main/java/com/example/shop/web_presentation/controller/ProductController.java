package com.example.shop.web_presentation.controller;

import com.example.shop.model.Brand;
import com.example.shop.model.Category;
import com.example.shop.model.Product;
import com.example.shop.model.Type;
import com.example.shop.service_business.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private CategoryService categoryService;
    private ProductService productService;
    private BrandService brandService;
    private ShoppingCartService shoppingCartService;
    private AuthService authService;
    private TypeService typeService;

    public ProductController(CategoryService categoryService,
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
    public String getProductPage(Model model) {
        List<Product> products = this.productService.findAll();
        List<Category> categories = this.categoryService.findAllParents();
        List<Brand> brands = this.brandService.findAll();
        Integer quantity = this.shoppingCartService.getProductQty(this.authService.getCurrentUserId());
        List<Type> types = this.typeService.findAll();
        model.addAttribute("types", types);
        model.addAttribute("productsquantity", quantity);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        // model.addAttribute("currency", "€");
        return "index";
    }

    @GetMapping("/brand/{id}")

    public String getBrandPage(Model model, @PathVariable Long id) {
        List<Product> products = this.productService.findAllByBrandId(id);
        List<Category> categories = this.brandService.findCategories(id);
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

    @GetMapping("/category/{id}")
    public String getCategoriesPage(Model model, @PathVariable Long id) {
        List<Category> categories = this.categoryService.findAllSubcategoriesNotNull();
        for (Category cat : categories) {
            if (cat.getId().equals(id)) {
                List<Category> subcategories = this.categoryService.findByParentId(id);
                Integer quantity = this.shoppingCartService.getProductQty(this.authService.getCurrentUserId());
                List<Product> products = this.productService.findAll();
                List<Brand> brands = this.brandService.findAll();
                List<Type> types = this.typeService.findAll();
                model.addAttribute("productsquantity", quantity);
                model.addAttribute("categories", subcategories);
                model.addAttribute("products", products);
                model.addAttribute("brands", brands);
                model.addAttribute("types", types);
                return "index";
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
