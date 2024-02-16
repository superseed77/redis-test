package org.formation.service;

import org.formation.entity.Product;
import org.formation.repository.ProductRepository;
import org.formation.util.CacheTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class ProductServiceRedis implements ProductService {

	@Autowired
	private CacheTemplate<Integer, Product> cache;

	@Override
	public Mono<Product> getProduct(int id) {
		return cache.get(id);
	}

	@Override
	public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
		return productMono.flatMap(p -> cache.update(id, p));
	}

	@Override
	public Mono<Void> deleteProduct(int id) {
		return cache.delete(id);
	}

	
	
	
}
