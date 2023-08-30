package com.example.cartservice.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cartservice.cart.dto.CartResponse;
import com.example.cartservice.cart.repository.CartRepository;
import com.example.cartservice.cart.service.CartService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/cartCB")
public class CartController {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartService cartService;

	@GetMapping("/")
	public List<CartResponse> getCarAll() {
		return CartResponse.of(cartRepository.findAll());
	}

	@GetMapping("/{id}")
	public CartResponse getCart(@PathVariable Long id) {
		return CartResponse.of(cartRepository.findById(id).orElseThrow());
	}

	// La llamaremos
	// /1/add?idProduct=2
	// /1/add?idProduct=2&quantity=8

	// He remodelado el método para que dé respuestas
	// Este fallará porque no levamtaremos el Catalogo
	@CircuitBreaker(name = "productCB", fallbackMethod = "fallBackAddProduct")
	@PostMapping("/{id}/add")
	public ResponseEntity<CartResponse> addProduct(@PathVariable Long id, @RequestParam Long idProduct,
			@RequestParam(defaultValue = "1") Integer quantity) {
		cartService.addProduct(id, idProduct, quantity);
		CartResponse cartResponse = CartResponse.of(cartRepository.findById(id).orElseThrow());
		if (cartResponse == null) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok(cartResponse);
		}
	}

	// Creo el metodo para Circuit Breaker que incluye la misma salida que el metodo
	// anterior
	// Los parametros d eentrada son lso mismos y añado una excepcion al finaL
	private ResponseEntity<CartResponse> fallBackAddProduct(@PathVariable Long id, @RequestParam Long idProduct,
			@RequestParam(defaultValue = "1") Integer quantity, RuntimeException e) {
		System.out.println("----------- fallBackAddProduct");
		return new ResponseEntity("Catalogo no disponible actualmente. Intentelo más tarde", HttpStatus.OK);

	}
}
