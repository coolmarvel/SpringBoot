package com.example.shop.model.exeptions;

public class BrandNotFoundException extends RuntimeException {
    public BrandNotFoundException(Long id) {
        super(String.format("Brand with id %d is not found!", id));
    }
}
