package com.bigdata.boot.chapter08;

import java.util.Date;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@RabbitListener(queues = "foo")
@EnableScheduling // Sender 中定义了定时任务，周期性触发，往 foo队列发送数据 ，被 RabbitHandler 接受处理
public class SampleAmqpSimpleApplication {

	@Bean
	public Sender mySender() {
		return new Sender();
	}

	@Bean
	public Queue fooQueue() {
		return new Queue("foo");
	}

	@RabbitHandler
	public void process(@Payload String message) {
		System.out.println(new Date() + ": " + message);
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleAmqpSimpleApplication.class, args);
	}

}