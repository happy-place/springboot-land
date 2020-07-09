package com.bigdata.boot.chapter20.dao;

import java.util.List;

import com.bigdata.boot.chapter20.model.Customer;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CustomerRepository extends Neo4jRepository<Customer, Long> {

	Customer findByFirstName(String firstName);

	List<Customer> findByLastName(String lastName);

}