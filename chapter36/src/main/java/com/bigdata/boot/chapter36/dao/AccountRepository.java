package com.bigdata.boot.chapter36.dao;

import com.bigdata.boot.chapter36.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}