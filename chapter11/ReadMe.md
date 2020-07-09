# Spring Batch
```text
批处理
```
## 项目结构
### config 
```
注册作业 三层，都已@Bean方式注册
Job -> Step -> task
```
### application
```java
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
```