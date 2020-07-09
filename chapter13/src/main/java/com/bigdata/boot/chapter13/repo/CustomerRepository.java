package com.bigdata.boot.chapter13.repo;

import java.util.List;

import com.bigdata.boot.chapter13.model.Customer;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CustomerRepository extends CassandraRepository<Customer,String> {

	@Query("Select * from customer where first_name=?0")
	Customer findByFirstName(String firstName);

	@Query("Select * from customer where last_name=?0")
	List<Customer> findByLastName(String lastName);

}