package org.formation.controller;

import org.formation.entity.Product;
import org.formation.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductControllerRedis {

	private ProductService service;

	public ProductControllerRedis(ProductService service) {
		this.service = service;
	}

	@GetMapping("{id}")
	public Mono<Product> getProductById(@PathVariable int id) {
		return this.service.getProduct(id);
	}

	@PutMapping("{id}")
	public Mono<Product> updateProduct(@PathVariable int id, @RequestBody Mono<Product> productDtoMono) {
		return this.service.updateProduct(id, productDtoMono);
	}

	@DeleteMapping("{id}")
	public Mono<Void> deleteProductById(@PathVariable int id) {
		return this.service.deleteProduct(id);
	}

}
