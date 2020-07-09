package com.bigdata.boot.chapter34.service;

import javax.transaction.Transactional;
import com.bigdata.boot.chapter34.dao.AccountRepository;
import com.bigdata.boot.chapter34.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

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