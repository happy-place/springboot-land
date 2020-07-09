package com.bigdata.boot.chapter23.dao;

import java.util.List;

import com.bigdata.boot.chapter23.model.Product;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface ProductRepository extends SolrCrudRepository<Product, String> {

	List<Product> findByNameStartingWith(String name);

}