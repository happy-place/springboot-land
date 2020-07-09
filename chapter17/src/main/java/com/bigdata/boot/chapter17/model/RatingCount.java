package com.bigdata.boot.chapter17.model;

import com.bigdata.boot.chapter17.model.enums.Rating;

public interface RatingCount {

	Rating getRating();

	long getCount();

}