package com.bigdata.boot.chapter16.repo;

import java.util.List;

import com.bigdata.boot.chapter16.model.Customer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	@Query("select id, first_name, date_of_birth from customer where upper(first_name) like '%' || upper(:name) || '%' ")
	List<Customer> nameLike(@Param("name") String name);

}