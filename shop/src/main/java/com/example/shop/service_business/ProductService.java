package com.example.shop.service_business;

import com.example.shop.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<Product> findAll();

    List<Product> findAllByBrandId(Long brandId);

    List<Product> findAllByCategoryId(Long categoryId);

    List<Product> findAllByTypeId(Long typeId);

    List<Product> findAllSortedByPrice(boolean asc);

    Product findById(Long id);

    Product saveProduct(String name, Float price, Integer quantity, Long brandId, Long categoryId, Long typeId);

    Product saveProduct(Product product, MultipartFile image) throws IOException;

    Product updateProduct(Long id, Product product, MultipartFile image) throws IOException;

    void deleteById(Long id);
}
