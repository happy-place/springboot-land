package com.bigdata.boot.chapter25.dao;

import com.bigdata.boot.chapter25.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

}