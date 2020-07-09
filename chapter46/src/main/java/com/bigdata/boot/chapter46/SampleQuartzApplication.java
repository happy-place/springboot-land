package com.bigdata.boot.chapter46;

import com.bigdata.boot.chapter46.job.SampleJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SampleQuartzApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleQuartzApplication.class, args);
	}

}