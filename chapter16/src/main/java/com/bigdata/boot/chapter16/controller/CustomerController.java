package com.bigdata.boot.chapter16.controller;

import java.util.List;

import com.bigdata.boot.chapter16.model.Customer;
import com.bigdata.boot.chapter16.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping(value = "/like")
	public List<Customer> nameLike(@RequestParam String name) {
		return customerService.nameLike(name);
	}

	@GetMapping
	public Customer getById(@RequestParam long id) {
		return customerService.findById(id);
	}

	@PostMapping
	public Customer addCustomer(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}

	@PutMapping
	public Customer updateCustomer(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}

	@DeleteMapping
	public void deleteById(@RequestParam long id) {
		customerService.deleteById(id);
	}

}