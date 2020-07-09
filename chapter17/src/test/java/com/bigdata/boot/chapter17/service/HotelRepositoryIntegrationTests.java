package com.bigdata.boot.chapter17.service;

import java.util.List;

import com.bigdata.boot.chapter17.dao.CityRepository;
import com.bigdata.boot.chapter17.dao.HotelRepository;
import com.bigdata.boot.chapter17.model.RatingCount;
import com.bigdata.boot.chapter17.model.City;
import com.bigdata.boot.chapter17.model.Hotel;
import com.bigdata.boot.chapter17.model.HotelSummary;
import com.bigdata.boot.chapter17.model.enums.Rating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelRepositoryIntegrationTests {

	@Autowired
	CityRepository cityRepository;

	@Autowired
	HotelRepository hotelRepository;

	@Test
	public void executesQueryMethodsCorrectly() {
		City city = cityRepository.findAll(PageRequest.of(0, 1, Direction.ASC, "name")).getContent().get(0);
		assertThat(city.getName()).isEqualTo("Atlanta");
		Page<HotelSummary> hotels = hotelRepository.findByCity(city, PageRequest.of(0, 10, Direction.ASC, "name"));
		Hotel hotel = hotelRepository.findByCityAndName(city, hotels.getContent().get(0).getName());
		assertThat(hotel.getName()).isEqualTo("Doubletree");
		List<RatingCount> counts = hotelRepository.findRatingCounts(hotel);
		assertThat(counts).hasSize(1);
		assertThat(counts.get(0).getRating()).isEqualTo(Rating.AVERAGE);
		assertThat(counts.get(0).getCount()).isGreaterThan(1L);
	}

}