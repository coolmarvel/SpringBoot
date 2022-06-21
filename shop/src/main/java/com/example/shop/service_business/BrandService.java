package com.example.shop.service_business;

import com.example.shop.model.Brand;
import com.example.shop.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BrandService {

    List<Brand> findAll();

    List<Brand> findAllByName(String name);

    Brand findById(Long id);

    Brand save(Brand brand);

    Brand update(Long id, Brand brand);

    Brand updateName(Long id, String name);

    void deleteById(Long id);

    Brand saveBrand(Brand brand, MultipartFile image) throws IOException;

    List<Category> findCategories(Long id);

}
