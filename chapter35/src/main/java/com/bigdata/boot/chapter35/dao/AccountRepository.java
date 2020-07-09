package com.bigdata.boot.chapter35.dao;

import com.bigdata.boot.chapter35.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}