# Flyway
```text
h2 启动初始化；
官方网站：http://www.flywaydb.org/
    Flyway是一个简单开源数据库版本控制器（约定大于配置），主要提供migrate、clean、info、validate、baseline、repair等命令。
它支持SQL（PL/SQL、T-SQL）方式和Java方式，支持命令行客户端等，还提供一系列的插件支持（Maven、Gradle、SBT、ANT等）。
```

## 查看sql版本
http://localhost:8080/actuator/flyway
```json
{
    "contexts": {
        "application": {
            "flywayBeans": {
                "flyway": {
                    "migrations": [
                        {
                            "type": "SQL",
                            "checksum": -54264251,
                            "version": "1",
                            "description": "init",
                            "script": "V1__init.sql",
                            "state": "SUCCESS",
                            "installedBy": "SA",
                            "installedOn": "2020-06-24T08:39:34.569Z",
                            "installedRank": 1,
                            "executionTime": 11
                        }
                    ]
                }
            },
            "parentId": null
        }
    }
}
```

## 内嵌式h2
* application.properties 可选配置
```properties
spring.datasource.url=jdbc:h2:mem:h2test
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.settings.web-allow-others=true
spring.h2.console.path=/h2-console
spring.h2.console.enabled=true

#spring.datasource.schema=classpath:db/schema.sql
#spring.datasource.data=classpath:db/data.sql
```
* 浏览器交互
```text
http://localhost:8080/h2-console/login.do?jsessionid=901171c83f83662d3591126b2b33dd0f
Generic H2（Embedded）
jdbc:h2:mem:h2test
```
