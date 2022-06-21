package com.example.shop.service_business.impl;

import com.example.shop.model.Brand;
import com.example.shop.model.Category;
import com.example.shop.model.exeptions.BrandNotFoundException;
import com.example.shop.repository.BrandRepository;
import com.example.shop.service_business.BrandService;
import com.example.shop.service_business.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final CategoryService categoryService;

    public BrandServiceImpl(BrandRepository brandRepository, CategoryService categoryService) {
        this.brandRepository = brandRepository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Brand> findAll() {
        return this.brandRepository.findAll();
    }

    @Override
    public List<Brand> findAllByName(String name) {
        return this.brandRepository.findAllByName(name);
    }

    @Override
    public Brand findById(Long id) {
        return this.brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException(id));
    }

    @Override
    public Brand save(Brand brand) {
        return this.brandRepository.save(brand);
    }

    @Override
    public Brand update(Long id, Brand brand) {
        Brand b = this.findById(id);
        b.setName(brand.getName());
        List<Category> categories = brand.getCategories();
        b.setCategories(categories);
        return this.brandRepository.save(b);
    }

    @Override
    public Brand updateName(Long id, String name) {
        Brand b = this.findById(id);
        b.setName(name);
        return this.brandRepository.save(b);
    }

    @Override
    public void deleteById(Long id) {
        this.brandRepository.deleteById(id);
    }

    @Override
    public Brand saveBrand(Brand brand, MultipartFile image) throws IOException {
        List<Category> categories = brand.getCategories();
        brand.setCategories(categories);
        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(),
                    Base64.getEncoder().encodeToString(bytes));
            brand.setImageBase64(base64Image);
        }
        return this.brandRepository.save(brand);

    }

    @Override
    public List<Category> findCategories(Long id) {
        Brand brand = this.brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException(id));
        List<Category> categories = brand.getCategories();
        return categories;
    }

}
