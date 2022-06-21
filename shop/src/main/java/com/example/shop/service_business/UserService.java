package com.example.shop.service_business;

import com.example.shop.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUsername(String userId);

    User registerUser(User user);

}
