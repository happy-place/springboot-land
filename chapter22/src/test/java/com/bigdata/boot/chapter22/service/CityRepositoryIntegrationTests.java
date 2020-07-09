package com.bigdata.boot.chapter22.service;

import com.bigdata.boot.chapter22.dao.CityRepository;
import com.bigdata.boot.chapter22.model.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityRepositoryIntegrationTests {

	@Autowired
	CityRepository repository;

	@Test
	public void findsFirstPageOfCities() {
		// cities.content.size = 10
		// cities.total = 21
		Page<City> cities = this.repository.findAll(PageRequest.of(0, 10));
		assertThat(cities.getTotalElements()).isGreaterThan(20L);
	}

	@Test
	public void findByNameAndCountry() {
		City city = this.repository.findByNameAndCountryAllIgnoringCase("Melbourne", "Australia");
		assertThat(city).isNotNull();
		assertThat(city.getName()).isEqualTo("Melbourne");
	}

	@Test
	public void findContaining() {
		Page<City> cities = this.repository
				.findByNameContainingAndCountryContainingAllIgnoringCase("", "uk",
						PageRequest.of(0, 10));
		assertThat(cities.getTotalElements()).isEqualTo(3L);
	}

}