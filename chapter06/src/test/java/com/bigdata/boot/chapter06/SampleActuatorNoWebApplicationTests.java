package com.bigdata.boot.chapter06;

import com.bigdata.boot.chapter06.component.HelloWorldService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleActuatorNoWebApplicationTests {

	@Autowired
	HelloWorldService helloWorldService;

	@Test
	public void contextLoads() {
		String helloMessage = helloWorldService.getHelloMessage();
		System.out.println(helloMessage);
	}

}