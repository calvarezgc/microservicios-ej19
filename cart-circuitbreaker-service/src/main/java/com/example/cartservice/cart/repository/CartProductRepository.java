package com.example.cartservice.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cartservice.cart.entity.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}

