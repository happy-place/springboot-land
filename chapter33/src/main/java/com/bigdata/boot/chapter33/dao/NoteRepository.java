package com.bigdata.boot.chapter33.dao;

import com.bigdata.boot.chapter33.model.Note;

import java.util.List;


public interface NoteRepository {

	List<Note> findAll();

}