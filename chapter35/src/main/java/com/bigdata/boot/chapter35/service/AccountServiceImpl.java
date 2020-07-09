package com.bigdata.boot.chapter35.service;

import com.bigdata.boot.chapter35.dao.AccountRepository;
import com.bigdata.boot.chapter35.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public void createAccountAndNotify(String username) {
		this.jmsTemplate.convertAndSend("accounts", username);
		this.accountRepository.save(new Account(username));
		if ("error".equals(username)) {
			throw new RuntimeException("Simulated error");
		}
	}

}