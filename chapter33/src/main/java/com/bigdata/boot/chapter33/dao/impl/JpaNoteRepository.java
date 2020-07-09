package com.bigdata.boot.chapter33.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.bigdata.boot.chapter33.dao.NoteRepository;
import com.bigdata.boot.chapter33.model.Note;
import org.springframework.stereotype.Repository;

@Repository
public class JpaNoteRepository implements NoteRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Note> findAll() {
		return this.entityManager.createQuery("SELECT n FROM Note n", Note.class)
				.getResultList();
	}

}