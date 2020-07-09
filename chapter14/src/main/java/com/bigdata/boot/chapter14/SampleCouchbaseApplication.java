package com.bigdata.boot.chapter14;

import java.util.UUID;

import com.bigdata.boot.chapter14.model.User;
import com.bigdata.boot.chapter14.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@SpringBootApplication
@EnableCouchbaseRepositories
public class SampleCouchbaseApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SampleCouchbaseApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {
		this.userRepository.deleteAll();
		User user = saveUser();
		System.out.println(this.userRepository.findById(user.getId()));
	}

	private User saveUser() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setFirstName("Alice");
		user.setLastName("Smith");
		return this.userRepository.save(user);
	}

}