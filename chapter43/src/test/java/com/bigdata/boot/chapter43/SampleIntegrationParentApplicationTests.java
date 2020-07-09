package com.bigdata.boot.chapter43;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.StreamUtils;

import static org.junit.Assert.fail;

public class SampleIntegrationParentApplicationTests {

	private static ConfigurableApplicationContext context;

	@BeforeClass
	public static void start() {
		context = SpringApplication.run(SampleParentContextApplication.class);
	}

	@AfterClass
	public static void stop() {
		if (context != null) {
			context.close();
		}
	}

	@Test
	public void testVanillaExchange() throws Exception {
		ProducerApplication.main(new String[]{"World"});
		awaitOutputContaining("Hello World");
	}

	private void awaitOutputContaining(String requiredContents) throws Exception {
		long endTime = System.currentTimeMillis() + 30000;
		String output = null;
		while (System.currentTimeMillis() < endTime) {
			Resource[] resources = findResources();
			if (resources.length == 0) {
				Thread.sleep(200);
				resources = findResources();
			}
			else {
				output = readResources(resources);
				if (output != null && output.contains(requiredContents)) {
					return;
				}
				else {
					Thread.sleep(200);
					output = readResources(resources);
				}
			}
		}
		fail("Timed out awaiting output containing '" + requiredContents + "'. Output was '" + output + "'");
	}

	private Resource[] findResources() throws IOException {
		return ResourcePatternUtils
				.getResourcePatternResolver(new DefaultResourceLoader())
				.getResources("file:data/output/*.txt");
	}

	private String readResources(Resource[] resources) throws IOException {
		StringBuilder builder = new StringBuilder();
		for (Resource resource : resources) {
			builder.append(new String(StreamUtils.copyToByteArray(resource.getInputStream())));
		}
		return builder.toString();
	}

}