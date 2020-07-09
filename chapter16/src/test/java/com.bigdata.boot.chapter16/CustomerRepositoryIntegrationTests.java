package com.bigdata.boot.chapter16;

import com.bigdata.boot.chapter16.repo.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class CustomerRepositoryIntegrationTests {

	@Autowired
	private CustomerRepository repository;

	@Test
	public void findAllCustomers() {
		assertThat(this.repository.findAll()).hasSize(2);
	}

	@Test
	public void findByNameWithMatch() {
		assertThat(this.repository.nameLike("joan")).hasSize(1);
	}

	@Test
	public void findByNameWithNoMatch() {
		assertThat(this.repository.nameLike("hugh")).isEmpty();
	}

}