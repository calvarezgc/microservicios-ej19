package com.example.cartservice.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cartservice.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}

