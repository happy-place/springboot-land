# spring-boot-sample-activemq

## 简介
```text
    MQ是消息中间件，是一种在分布式系统中应用程序借以传递消息的媒介，常用的有ActiveMQ，RabbitMQ，kafka。ActiveMQ是Apache下的开源项目，
完全支持JMS1.1和J2EE1.4规范的JMS Provider实现。
```
## 特点
1. 支持多种语言编写客户端 
2. 对spring的支持，很容易和spring整合 
3. 支持多种传输协议：TCP,SSL,NIO,UDP等 
4. 支持AJAX 

## 消息形式 
1. 点对点（queue） 
2. 一对多（topic）

## 简单项目构成
* pom依赖
```xml
    <!-- 通过parent 传递springboot相关依赖-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.1.RELEASE</version>
        <relativePath/>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring-boot.version>2.1.1.RELEASE</spring-boot.version>
    </properties>

    <dependencies>
        <!-- Compile -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-activemq</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>
        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

```

* Application
```java
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@SpringBootApplication
@EnableJms
public class SampleActiveMQApplication {

	// 直接在 App 中声明 Queue
    /**
	@Bean
	*public Queue queue() {
	*	return new ActiveMQQueue("sample.queue");
	*}
    */
	public static void main(String[] args) {
		SpringApplication.run(SampleActiveMQApplication.class, args);
	}

}
```


* Queue
```java 
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.jms.Queue;

@Configuration
public class SpringConfig {

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("sample.queue");
    }

}
```
* application.properties
```properties
spring.activemq.in-memory=true
spring.activemq.pool.enabled=false
```

* Producer 
```java
import javax.jms.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component(value = "producer")
public class Producer implements CommandLineRunner {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private Queue queue;

	@Override
	public void run(String... args) throws Exception {
		send("Sample message");
		System.out.println("Message was sent to the Queue");
	}

	public void send(String msg) {
		this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
	}

}
```

 * Consumer
```java
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	@JmsListener(destination = "sample.queue")
	public void receiveQueue(String text) {
		System.out.println(text);
	}

}
```

## 测试
* SampleActiveMqTests （包名需要与Application所在包一致）
```java
import com.bigdata.boot.chapter03.example1.jms.Producer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

// spring-boot-starter-test，运行过程自动启动IOC容器
@RunWith(SpringRunner.class)
@SpringBootTest 
public class SampleActiveMqTests {

    // 捕获print信息
	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Autowired // 从IOC容器注入生产者
	private Producer producer;

	@Test
	public void sendSimpleMessage() throws InterruptedException {
		this.producer.send("Test message");
		Thread.sleep(1000L);
        // 断言消费者输出包含指定内容
		assertThat(this.outputCapture.toString().contains("Test message")).isTrue(); 
	}

}
```