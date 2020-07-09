# Cache
```text
Any compliant JSR-107 (JCache) provider
* spring 内聚缓存插件
* EhCache
* Hazelcast
* Infinispan
* Couchbase
* Redis
* Caffine
* Simple provider based on ConcurrentHashMap
* Generic provider based on org.springframework.Cache bean definition(s)
```

## 项目结构
### Model
```text
Country 对应 持久化模型
```
### Dao
```text
CountryRepository 对应sql
```
### Service
```text
略 主打事务（原本应该放置缓存）
```
### Mock
```text
@Profile("app")  // 只有在 run 时 指定 profile 与标记profile 匹配时，才会被注册到IOC容器
SampleClient 模拟客户端刷数据加载到缓存
```
### Application
```java
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class SampleCacheApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder()
            .sources(SampleCacheApplication.class) 
            .profiles("app").run(args); // 加载指定配置，呼应 @Profile 注解
	}

}
```





