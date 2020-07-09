package com.bigdata.boot.chapter22.dao;

import com.bigdata.boot.chapter22.model.City;
import com.bigdata.boot.chapter22.model.Hotel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "hotels", path = "hotels")
interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {

	Hotel findByCityAndName(City city, String name);

}