package com.bigdata.boot.chapter36;

import com.bigdata.boot.chapter36.dao.AccountRepository;
import com.bigdata.boot.chapter36.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SampleJNDIApplication implements CommandLineRunner {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public void run(String... args) throws Exception {
		accountService.createAccountAndNotify("josh");
		log.info("Count is " + accountRepository.count());
		try {
			// 手动抛出异常，然后检测是否入库了？从而验证JTA的事务能力
			accountService.createAccountAndNotify("error");
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		log.info("Count is " + accountRepository.count());
		Thread.sleep(100);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleJNDIApplication.class, args).close();
	}

}