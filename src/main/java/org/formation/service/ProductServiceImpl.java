package org.formation.service;

import org.formation.entity.Product;
import org.formation.repository.ProductRepository;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

//@Service
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository repository;

	public ProductServiceImpl(ProductRepository repository) {
		this.repository = repository;
	}

	@Override
	public Mono<Product> getProduct(int id) {
		return repository.findById(id);
	}

	@Override
	public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
		return getProduct(id)
				.flatMap(p -> productMono.doOnNext(product -> product.setId(id)))
				.flatMap(repository::save);
	}

	@Override
	public Mono<Void> deleteProduct(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
