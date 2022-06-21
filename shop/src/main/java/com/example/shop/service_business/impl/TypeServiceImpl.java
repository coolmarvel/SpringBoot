package com.example.shop.service_business.impl;

import com.example.shop.model.Type;
import com.example.shop.model.exeptions.TypeNotFoundException;
import com.example.shop.repository.TypeRepository;
import com.example.shop.service_business.TypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public List<Type> findAll() {
        return this.typeRepository.findAll();
    }

    @Override
    public Type findById(Long id) {
        return this.typeRepository.findById(id).orElseThrow(()-> new TypeNotFoundException(id));
    }

    @Override
    public Type save(Type type) {
        return this.typeRepository.save(type);
    }

    @Override
    public Type update(Long id, Type type) {
        Type type1 = this.findById(id);
        type1.setName(type.getName());
        type1.setProducts(type.getProducts());
        return this.typeRepository.save(type1);
    }

    @Override
    public Type updateName(Long id, String name) {
        Type type = this.findById(id);
        type.setName(name);
        return this.typeRepository.save(type);
    }

    @Override
    public void deleteById(Long id) {
        this.typeRepository.deleteById(id);
    }
}
