package com.bigdata.boot.chapter17.service;

import com.bigdata.boot.chapter17.model.City;
import com.bigdata.boot.chapter17.model.CitySearchCriteria;

import com.bigdata.boot.chapter17.model.HotelSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

	Page<City> findCities(CitySearchCriteria criteria, Pageable pageable);

	City getCity(String name, String country);

	Page<HotelSummary> getHotels(City city, Pageable pageable);

}