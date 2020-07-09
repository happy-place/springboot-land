package com.bigdata.boot.chapter34.dao;

import com.bigdata.boot.chapter34.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}