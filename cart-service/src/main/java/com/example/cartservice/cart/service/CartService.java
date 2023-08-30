package com.example.cartservice.cart.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cartservice.cart.dto.ProductResponse;
import com.example.cartservice.cart.entity.Cart;
import com.example.cartservice.cart.entity.CartProduct;
import com.example.cartservice.cart.repository.CartProductRepository;
import com.example.cartservice.cart.repository.CartRepository;
import com.example.cartservice.feignclients.CatalogFeignClient;

@Transactional
@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartProductRepository cartProductRepository;

	@Autowired
	private CatalogFeignClient catalogFeign;

	public void addProduct(Long id, Long idProduct, Integer quantity) {
		final ProductResponse product = catalogFeign.getProduct(idProduct);
		this.addProduct(id, idProduct, product.getPrice(), product.getName(), quantity);
	}

	public void addProduct(Long id, Long idProduct, BigDecimal price, String name, Integer quantity) {
		final Cart cart = cartRepository.findById(id).orElseThrow();

		for (int i = 0; i < quantity; i++) {
			CartProduct cp = new CartProduct();
			cp.setCart(cart);
			cp.setProduct_id(idProduct);
			cp.setPrice(price);
			cp.setName(name);
			cartProductRepository.save(cp);
		}
	}
}
