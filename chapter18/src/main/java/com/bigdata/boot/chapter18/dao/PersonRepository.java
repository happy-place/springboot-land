package com.bigdata.boot.chapter18.dao;

import com.bigdata.boot.chapter18.model.Person;
import org.springframework.data.ldap.repository.LdapRepository;

public interface PersonRepository extends LdapRepository<Person> {

	Person findByPhone(String phone);

}