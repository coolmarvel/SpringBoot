package com.exam.registlogin.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.exam.registlogin.model.User;
import com.exam.registlogin.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}