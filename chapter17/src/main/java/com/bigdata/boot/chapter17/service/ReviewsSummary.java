package com.bigdata.boot.chapter17.service;


import com.bigdata.boot.chapter17.model.enums.Rating;

public interface ReviewsSummary {

	long getNumberOfReviewsWithRating(Rating rating);

}