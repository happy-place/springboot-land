package com.bigdata.boot.chapter21;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.data.redis.RedisConnectionFailureException;
import redis.embedded.RedisCluster;
import redis.embedded.RedisServer;
import redis.embedded.util.JedisUtil;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleRedisApplicationTests {

	private RedisServer redisServer;

	@Before
	public void setup() throws Exception {
		// https://github.com/kstyrc/embedded-redis
		redisServer =  new RedisServer(6379);
//		redisServer =  new RedisServer("redis 可执行文件", 6379);
		redisServer.start();
	}

	@After
	public void tearDown() throws Exception {
		redisServer.stop();
	}

	@Rule
	public OutputCaptureRule outputCapture = new OutputCaptureRule();

	@Test
	public void testDefaultSettings() {
		try {
			SampleRedisApplication.main(new String[0]);
		}
		catch (Exception ex) {
			if (!redisServerRunning(ex)) {
				return;
			}
		}
		String output = this.outputCapture.getOut();
		assertThat(output).contains("Found key spring.boot.redis.test");
	}

	private boolean redisServerRunning(Throwable ex) {
		System.out.println(ex.getMessage());
		if (ex instanceof RedisConnectionFailureException) {
			return false;
		}
		return (ex.getCause() == null || redisServerRunning(ex.getCause()));
	}

}