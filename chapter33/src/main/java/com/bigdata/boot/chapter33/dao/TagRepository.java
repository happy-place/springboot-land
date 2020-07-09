package com.bigdata.boot.chapter33.dao;

import com.bigdata.boot.chapter33.model.Tag;

import java.util.List;


public interface TagRepository {

	List<Tag> findAll();

}