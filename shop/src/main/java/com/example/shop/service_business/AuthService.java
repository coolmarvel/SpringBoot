package com.example.shop.service_business;

import com.example.shop.model.User;

public interface AuthService {
    User getCurrentUser();

    String getCurrentUserId();

    User signUpUser(String username, String password, String repeatedPassword);
}
