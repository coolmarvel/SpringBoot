package com.example.shop.service_business;

import com.example.shop.model.Type;

import java.util.List;

public interface TypeService {
    List<Type> findAll();

    Type findById(Long id);

    Type save(Type type);

    Type update(Long id, Type type);

    Type updateName(Long id, String name);

    void deleteById(Long id);
}
