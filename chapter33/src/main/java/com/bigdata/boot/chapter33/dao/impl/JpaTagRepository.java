package com.bigdata.boot.chapter33.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.bigdata.boot.chapter33.dao.TagRepository;
import com.bigdata.boot.chapter33.model.Tag;
import org.springframework.stereotype.Repository;

@Repository
public class JpaTagRepository implements TagRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Tag> findAll() {
		return this.entityManager.createQuery("SELECT t FROM Tag t", Tag.class)
				.getResultList();
	}

}