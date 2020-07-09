package com.bigdata.boot.chapter10;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.boot.test.rule.OutputCapture;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SampleAopApplication}.
 *
 * @author Dave Syer
 * @author Phillip Webb
 */
public class SampleAopApplicationTests {

	// 捕捉标准输出
	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	private String profiles;

	@Before
	public void init() {
		// 初始化时获取生效的配置环境
		this.profiles = System.getProperty("spring.profiles.active");
	}

	@After
	public void after() {
		if (this.profiles != null) {
			// 环境不为空，就设置会当前环境
			System.setProperty("spring.profiles.active", this.profiles);
		}
		else {
			// 环境为空，就清空存在的键
			System.clearProperty("spring.profiles.active");
		}
	}

	@Test
	public void testDefaultSettings() throws Exception {
		SampleAopApplication.main(new String[0]); // main 中传入空数组
		String output = this.outputCapture.toString();
		assertThat(output).contains("Hello Phil"); // 从 application.properties 中 取默认属性值
	}

	@Test
	public void testCommandLineOverrides() throws Exception {
		SampleAopApplication.main(new String[] { "--name=Gordon" }); // 从 入参 覆盖默认属性值
		String output = this.outputCapture.toString();
		assertThat(output).contains("Hello Gordon");
	}

}