package com.bigdata.boot.chapter73;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class SampleWebSecureJdbcApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SampleWebSecureJdbcApplication.class).run(args);
    }

}