package com.example.shop.service_business.impl;

import com.example.shop.model.Role;
import com.example.shop.model.User;
import com.example.shop.repository.RoleRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service_business.AuthService;
import com.example.shop.service_business.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Profile("oauth2")
public class OAuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public OAuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Role userRole = this.roleRepository.findByName("ROLE_USER");
        User user = new User(currentPrincipalName, true, true, true, true, Collections.singletonList(userRole));
        if (!this.userRepository.existsById(currentPrincipalName)) {
            this.userRepository.save(user);
        }
        return user;
    }

    @Override
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Role userRole = this.roleRepository.findByName("ROLE_USER");
        User user = new User(currentPrincipalName, true, true, true, true, Collections.singletonList(userRole));

        if (!this.userRepository.existsById(currentPrincipalName)) {
            this.userRepository.save(user);
        }
        return currentPrincipalName;
    }

    @Override
    public User signUpUser(String username, String password, String repeatedPassword) {
        return null;
    }
}
