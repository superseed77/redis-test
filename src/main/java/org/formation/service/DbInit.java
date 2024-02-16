package org.formation.service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

import org.formation.entity.Product;
import org.formation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DbInit implements CommandLineRunner{
	@Value("classpath:schema.sql")
	private Resource resource;

	private ProductRepository repository;
	private R2dbcEntityTemplate entityTemplate;

	public DbInit(ProductRepository repository, R2dbcEntityTemplate entityTemplate) {
		this.repository = repository;
		this.entityTemplate = entityTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		String query = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
		System.out.println(query);

		Mono<Void> insert = Flux.range(1, 1000)
				.map(i -> new Product("product" + i, ThreadLocalRandom.current().nextInt(1, 100)))
				.collectList()
				//on obteint une liste de product
				//Mais c'est un Mono<List> 
				// saveAll retourne un Flux
				// il faut donc utiliser flatmap Many 
				.flatMapMany(l -> this.repository.saveAll(l))
				.then();

		entityTemplate.getDatabaseClient().sql(query)
		.then() // celui-ci vient de entityTemplate et execute le script
		.then(insert) // celui-ci attend la fin du premier
		.doFinally(s -> System.out.println("data setup done " + s)) //s'execute a la terminaison complete cancel ou error
		.subscribe();

	}

}
