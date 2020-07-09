package com.bigdata.boot.chapter45;

import com.bigdata.boot.chapter45.component.SampleProperties;
import org.junit.After;
import org.junit.Test;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link SamplePropertyValidationApplication}.
 *
 * @author Lucas Saldanha
 * @author Stephane Nicoll
 */
public class SamplePropertyValidationApplicationTests {

	private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

	@After
	public void closeContext() {
		this.context.close();
	}

	@Test
	public void bindValidProperties() {
		this.context.register(SamplePropertyValidationApplication.class);
		TestPropertyValues.of("sample.host:192.168.0.1", "sample.port:9090").applyTo(this.context);
		this.context.refresh();
		SampleProperties properties = this.context.getBean(SampleProperties.class);
		assertThat(properties.getHost()).isEqualTo("192.168.0.1");
		assertThat(properties.getPort()).isEqualTo(Integer.valueOf(9090));
	}

	@Test
	public void bindInvalidHost() {
		this.context.register(SamplePropertyValidationApplication.class);
		TestPropertyValues.of("sample.host:xxxxxx", "sample.port:9090")
				.applyTo(this.context);
		assertThatExceptionOfType(BeanCreationException.class)
				.isThrownBy(() -> this.context.refresh())
				.withMessageContaining("Failed to bind properties under 'sample'");
	}

	@Test
	public void bindNullHost() {
		this.context.register(SamplePropertyValidationApplication.class);
		assertThatExceptionOfType(BeanCreationException.class)
				.isThrownBy(() -> this.context.refresh())
				.withMessageContaining("Failed to bind properties under 'sample'");
	}

	@Test
	public void validatorOnlyCalledOnSupportedClass() {
		this.context.register(SamplePropertyValidationApplication.class);
		this.context.register(ServerProperties.class); // our validator will not apply
		TestPropertyValues.of("sample.host:192.168.0.1", "sample.port:9090")
				.applyTo(this.context);
		this.context.refresh(); // 刷新properties
		SampleProperties properties = this.context.getBean(SampleProperties.class);
		assertThat(properties.getHost()).isEqualTo("192.168.0.1");
		assertThat(properties.getPort()).isEqualTo(Integer.valueOf(9090));
	}

}