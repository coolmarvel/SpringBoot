package com.example.shop.model.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TypeNotFoundException extends RuntimeException {
    public TypeNotFoundException(Long id) {
        super(String.format("Type with id %d is not found!", id));
    }
}
