package com.example.shop.service_business.impl;

import com.example.shop.model.*;
import com.example.shop.model.enumerations.CartStatus;
import com.example.shop.model.exeptions.ProductIsAlreadyInShoppingCartException;
import com.example.shop.model.exeptions.ProductNotFoundException;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service_business.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final IngredientService ingredientService;

    public ProductServiceImpl(ProductRepository productRepository, BrandService brandService,
            CategoryService categoryService, TypeService typeService, IngredientService ingredientService) {
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.typeService = typeService;
        this.ingredientService = ingredientService;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public List<Product> findAllByBrandId(Long brandId) {
        return this.productRepository.findAllByBrandId(brandId);
    }

    @Override
    public List<Product> findAllByCategoryId(Long categoryId) {
        return this.productRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Product> findAllByTypeId(Long typeId) {
        return this.productRepository.findAllByTypeId(typeId);
    }

    @Override
    public List<Product> findAllSortedByPrice(boolean asc) {
        if (asc) {
            return this.productRepository.findAllByOrderByPriceAsc();
        }
        return this.productRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public Product findById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product saveProduct(String name, Float price, Integer quantity, Long brandId, Long categoryId, Long typeId) {
        Brand brand = this.brandService.findById(brandId);
        Category category = this.categoryService.findById(categoryId);
        Type type = this.typeService.findById(typeId);
        // List<Ingredient> ingredients = this.ingredientService.findAll();
        Product product = new Product(null, name, price, quantity, brand, category, type);
        return this.productRepository.save(product);
    }

    @Override
    public Product saveProduct(Product product, MultipartFile image) throws IOException {
        Brand brand = this.brandService.findById(product.getBrand().getId());
        product.setBrand(brand);
        Category category = this.categoryService.findById(product.getCategory().getId());
        product.setCategory(category);
        Type type = this.typeService.findById(product.getType().getId());
        product.setType(type);
        List<Ingredient> ingredients = product.getIngredients();
        product.setIngredients(ingredients);
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(),
                    Base64.getEncoder().encodeToString(bytes));
            product.setImageBase64(base64Image);
        }
        return this.productRepository.save(product);

    }

    @Override
    public Product updateProduct(Long id, Product product, MultipartFile image) throws IOException {

        Product product1 = this.findById(id);
        Brand brand = this.brandService.findById(product.getBrand().getId());
        Category category = this.categoryService.findById(product.getCategory().getId());
        Type type = this.typeService.findById(product.getType().getId());
        List<Ingredient> ingredients = product.getIngredients();
        product1.setIngredients(ingredients);
        product1.setBrand(brand);
        product1.setCategory(category);
        product1.setType(type);
        product1.setPrice(product.getPrice());
        product1.setQuantity(product.getQuantity());
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(),
                    Base64.getEncoder().encodeToString(bytes));
            product1.setImageBase64(base64Image);
        }
        return this.productRepository.save(product1);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Product product = this.findById(id);
        for (CartItem cartItem : product.getCartItems()) {
            if (cartItem.getShoppingCart().getStatus().equals(CartStatus.CREATED))
                throw new ProductIsAlreadyInShoppingCartException(product.getName());
        }
        this.productRepository.deleteById(id);
    }

}
