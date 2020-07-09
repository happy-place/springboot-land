package com.bigdata.boot.chapter18;

import com.bigdata.boot.chapter18.dao.PersonRepository;
import com.bigdata.boot.chapter18.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

@SpringBootApplication
@EnableLdapRepositories
public class SampleLdapApplication implements CommandLineRunner {

	@Autowired
	PersonRepository repository;

	public SampleLdapApplication(PersonRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... args) throws Exception {
		// fetch all people
		System.out.println("People found with findAll():");
		System.out.println("-------------------------------");
		for (Person person : repository.findAll()) {
			System.out.println(person);
		}
		System.out.println();

		// fetch an individual person
		System.out.println("Person found with findByPhone('+46 555-123456'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByPhone("+46 555-123456"));
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleLdapApplication.class, args).close();
	}

}