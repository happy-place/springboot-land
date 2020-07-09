package com.bigdata.boot.chapter11;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing // 开启批处理
public class SampleBatchApplication {

	public static void main(String[] args) {
		// spring app 退出状态码就是系统应用退出状态码
		System.exit(
			SpringApplication.exit(
				SpringApplication.run(SampleBatchApplication.class, args) // run 执行时就开始执行 batch job
			)
		);
	}

}