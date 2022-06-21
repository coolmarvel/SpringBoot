package com.example.shop.web_presentation.controller;

import com.example.shop.model.*;
import com.example.shop.model.exeptions.ProductIsAlreadyInShoppingCartException;
import com.example.shop.service_business.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ProductService productService;
    private BrandService brandService;
    private CategoryService categoryService;
    private TypeService typeService;
    private ShoppingCartService shoppingCartService;
    private AuthService authService;
    private IngredientService ingredientService;

    public AdminController(ProductService productService,
            BrandService brandService,
            CategoryService categoryService,
            TypeService typeService, ShoppingCartService shoppingCartService, AuthService authService,
            IngredientService ingredientService) {
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.typeService = typeService;
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public String getProductPage(Model model) {
        List<Product> products = this.productService.findAll();
        List<Category> categories = this.categoryService.findAllParents();
        List<Brand> brands = this.brandService.findAll();
        List<Type> types = this.typeService.findAll();
        Integer quantity = this.shoppingCartService.getProductQty(this.authService.getCurrentUserId());
        model.addAttribute("productsquantity", quantity);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("types", types);
        return "admin";
    }

    @GetMapping("/add-product")
    public String addNewProductPage(Model model) {
        List<Brand> brands = this.brandService.findAll();
        List<Category> subcategories = this.categoryService.findAllSubcategories();
        List<Type> types = this.typeService.findAll();
        List<Ingredient> ingredients = this.ingredientService.findAll();
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("types", types);
        model.addAttribute("brands", brands);
        model.addAttribute("categories", subcategories);
        model.addAttribute("product", new Product());

        return "add-product";
    }

    @GetMapping("/{id}/edit")
    public String editProductPage(Model model, @PathVariable Long id) {
        try {
            Product product = this.productService.findById(id);
            List<Brand> brands = this.brandService.findAll();
            List<Category> subcategories = this.categoryService.findAllSubcategories();
            List<Type> types = this.typeService.findAll();
            List<Ingredient> ingredients = this.ingredientService.findAll();
            model.addAttribute("ingredients", ingredients);
            model.addAttribute("product", product);
            model.addAttribute("brands", brands);
            model.addAttribute("categories", subcategories);
            model.addAttribute("types", types);
            return "add-product";
        } catch (RuntimeException ex) {
            return "redirect:/admin?error=" + ex.getMessage();
        }
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public String saveProduct(
            @Valid Product product,
            BindingResult bindingResult,
            @RequestParam MultipartFile image,
            Model model) {

        if (bindingResult.hasErrors()) {
            List<Brand> brands = this.brandService.findAll();
            model.addAttribute("brands", brands);
            List<Category> categories = this.categoryService.findAllSubcategories();
            model.addAttribute("categories", categories);
            List<Type> types = this.typeService.findAll();
            model.addAttribute("types", types);
            List<Ingredient> ingredients = this.ingredientService.findAll();
            model.addAttribute("ingredients", ingredients);
            return "add-product";
        }
        try {
            this.productService.saveProduct(product, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteProductWithPost(@PathVariable Long id) {
        try {
            this.productService.deleteById(id);
        } catch (ProductIsAlreadyInShoppingCartException ex) {
            return String.format("redirect:/admin?error=%s", ex.getMessage());
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteProductWithDelete(@PathVariable Long id) {
        this.productService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/add-brand")
    public String addNewBrandPage(Model model) {
        List<Category> subcategories = this.categoryService.findAllSubcategories();
        model.addAttribute("categories", subcategories);
        model.addAttribute("brand", new Brand());

        return "add-brand";
    }

    @GetMapping("/brand/{id}/edit")
    public String editBrandPage(Model model, @PathVariable Long id) {
        try {
            Brand brand = this.brandService.findById(id);
            List<Category> subcategories = this.categoryService.findAllSubcategories();
            model.addAttribute("brand", brand);
            model.addAttribute("categories", subcategories);
            return "add-brand";
        } catch (RuntimeException ex) {
            return "redirect:/admin?error=" + ex.getMessage();
        }
    }

    @PostMapping("/brand")
    @Secured("ROLE_ADMIN")
    public String saveBrand(
            @Valid Brand brand,
            BindingResult bindingResult,
            @RequestParam MultipartFile image,
            Model model) {

        if (bindingResult.hasErrors()) {
            List<Category> categories = this.categoryService.findAllSubcategories();
            model.addAttribute("categories", categories);
            return "add-brand";
        }
        try {
            this.brandService.saveBrand(brand, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }

    @GetMapping("/brand/{id}")

    public String getBrandPage(Model model, @PathVariable Long id) {
        List<Product> products = this.productService.findAllByBrandId(id);
        List<Category> categories = this.brandService.findCategories(id);
        List<Type> types = this.typeService.findAll();
        model.addAttribute("types", types);
        List<Brand> brands = this.brandService.findAll();
        Integer quantity = this.shoppingCartService.getProductQty(this.authService.getCurrentUserId());
        model.addAttribute("productsquantity", quantity);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        // model.addAttribute("subcategories", subcategories);
        model.addAttribute("brands", brands);
        return "admin";
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
                return "admin";
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
        return "admin";

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
