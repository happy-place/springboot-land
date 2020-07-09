package com.bigdata.boot.chapter43;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ProducerApplication {

	public void run(String... args) throws Exception {
		new File("data/input").mkdirs();
		if (args.length > 0) {
			FileOutputStream stream = new FileOutputStream("data/input/data" + System.currentTimeMillis() + ".txt");
			for (String arg : args) {
				stream.write(arg.getBytes());
			}
			stream.flush();
			stream.close();
		}
	}

	public static void main(String[] args) throws Exception {
		new ProducerApplication().run(args);
	}

}