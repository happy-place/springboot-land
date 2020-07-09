package com.bigdata.boot.chapter17.service;

import com.bigdata.boot.chapter17.model.City;
import com.bigdata.boot.chapter17.model.Hotel;
import com.bigdata.boot.chapter17.model.Review;
import com.bigdata.boot.chapter17.model.ReviewDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelService {

	Hotel getHotel(City city, String name);

	Page<Review> getReviews(Hotel hotel, Pageable pageable);

	Review getReview(Hotel hotel, int index);

	Review addReview(Hotel hotel, ReviewDetails details);

	ReviewsSummary getReviewSummary(Hotel hotel);

}