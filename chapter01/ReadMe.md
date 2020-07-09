# 快速搭建springboot项目
## maven 依赖
```xml
   <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring-boot.version>2.1.1.RELEASE</spring-boot.version>
    </properties>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.1.RELEASE</version>
        <relativePath/>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>${spring-boot.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>${spring-boot.version}</version>
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
## 编码
### EnableAutoConfiguration
```java
@RestController
@EnableAutoConfiguration // 自动配置项目
@RequestMapping(value = "/simple-rest") // 当前controller映射的路径
public class Example {

    @GetMapping(value = "/hi") // 当前方法映射的路径
    public String sayHi(){
        return "hello world!";
    }

    public static void main(String[] args) {
        // Controller与Application写在一起
        SpringApplication.run(Example.class,args);
    }

}
```
### SpringBootApplication
```java
// Application.java
@SpringBootApplication // 已经包含了 EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
```
```java
// SimpleController.java
@RestController
@RequestMapping(value = "/simple")
public class SimpleController {

    @GetMapping(value = "/hi")
    public String sayHi(){
        return "Hello, world.";
    }

}
```
## 启动
### IDEA运行
```
右键 > Run
``` 
### 插件启动
```shell script
# 启动应用
mvn spring-boot:run

# 查看maven 依赖
mvn dependency:tree
``` 
### jar运行
```shell script
# 打包
mvn package
# 运行
java -jar target/hello-world-1.0-SNAPSHOT.jar 
```

## 访问
```shell script
curl http://localhost:8080/simple/hi
```

## 官网示例
[springboot-example](!https://github.com/spring-projects/spring-boot/tree/v2.1.1.RELEASE/spring-boot-samples)