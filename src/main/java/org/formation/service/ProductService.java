package org.formation.service;

import org.formation.entity.Product;

import reactor.core.publisher.Mono;

public interface ProductService {

	Mono<Product> getProduct( int id);
	
	Mono<Product> updateProduct( int id, Mono<Product> productMono);
	

	Mono<Void> deleteProduct( int id);
	
	

	
}
