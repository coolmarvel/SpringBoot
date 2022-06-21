package com.example.shop.repository;


import com.example.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
   User findByUsername(String username);
   Optional<User> findApplicationUserByUsername(String username);
}
