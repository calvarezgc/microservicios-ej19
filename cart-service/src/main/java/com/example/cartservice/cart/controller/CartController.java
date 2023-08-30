package com.example.cartservice.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cartservice.cart.dto.CartResponse;
import com.example.cartservice.cart.repository.CartRepository;
import com.example.cartservice.cart.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
    @Autowired 
    private CartRepository cartRepository;
    
    @Autowired 
    private CartService cartService;
    
    @GetMapping("/")
    public List<CartResponse> getCarAll(){
    	return CartResponse.of(cartRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public CartResponse getCart(@PathVariable Long id) {
    	return CartResponse.of(cartRepository.findById(id).orElseThrow());
    }
    
  //La llamaremos  
    //    /1/add?idProduct=2
    //    /1/add?idProduct=2&quantity=8
    @PostMapping("/{id}/add")
    public void addProduct(@PathVariable Long id, @RequestParam Long idProduct, @RequestParam(defaultValue = "1") Integer quantity) {
    	//En este caso este service va a usar un cliente Feign
        cartService.addProduct(id, idProduct, quantity);
    }
}
