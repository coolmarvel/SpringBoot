package com.example.shop.repository;



import com.example.shop.model.ShoppingCart;

import com.example.shop.model.enumerations.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserUsernameAndStatus(String username, CartStatus status);
//    Optional<ShoppingCart> findByApplicationUserUsernameAndStatus(String username, CartStatus status);
    boolean existsByUserUsernameAndStatus(String username, CartStatus status);
//    boolean existsByApplicationUserUsernameAndStatus(String username, CartStatus status);
    List<ShoppingCart> findAllByUserUsername(String username);
//    List<ShoppingCart> findAllByApplicationUserUsername(String username);
    ShoppingCart findByUserUsername(String username);

}
