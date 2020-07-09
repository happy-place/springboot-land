package com.bigdata.boot.chapter25;

import com.bigdata.boot.chapter25.dao.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleFlywayApplication implements CommandLineRunner{

	@Autowired
	PersonRepository personRepository;

	@Override
	public void run(String... args) throws Exception {
		System.err.println(personRepository.findAll());
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleFlywayApplication.class, args);
	}

}