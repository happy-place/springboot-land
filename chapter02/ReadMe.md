# 获取官方打包好的依赖

## spring-boot依赖
### 方案1
```xml
   <!-- 借助parent解决依赖收集和版本兼容问题 -->
   <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.1.RELEASE</version>
        <relativePath/>
    </parent>
```

### 方案2
```xml
    <!--父pom中引入spring-boot-dependencies、spring-data-releasetrain 子项目使用具体插件时，如果与springboot官方推荐版本冲突，使用用户的版本覆盖springboot推荐版本-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.data</groupId>
                <artifactId>spring-data-releasetrain</artifactId>
                <version>${springboot-releasetrain.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

## 清单
|名称|描述|
|:-----:|:-----:|
| spring-boot-starter | 核心启动器，包括自动配置支持，日志记录和YAML |
| spring-boot-starter-activemq | 使用Apache ActiveMQ进行JMS消息传递的入门者 |
| spring-boot-starter-amqp | 使用Spring AMQP和Rabbit MQ的入门者 |
| spring-boot-starter-aop | 使用Spring AOP和AspectJ进行面向方面编程的入门者 |
| spring-boot-starter-artemis | 使用Apache Artemis进行JMS消息传递的入门者 |
| spring-boot-starter-batch | 使用Spring批处理的初学者 |
| spring-boot-starter-batch | 使用Spring批处理的初学者 |
| spring-boot-starter-cache | 使用Spring Framework的缓存支持的初学者 |
| spring-boot-starter-cloud-connectors | 使用Spring云连接器的初学者简化了与Cloud Foundry和Heroku等云平台中的服务的连接 |
| spring-boot-starter-data-cassandra | 使用Cassandra分布式数据库和Spring数据的初学者Cassandra |
| spring-boot-starter-data-cassandra-reactive | 使用Cassandra分布式数据库和Spring数据Cassandra Reactive的入门者 |
| spring-boot-starter-data-couchbase | 使用Couchbase面向文档的数据库和Spring Data Couchbase的入门者 |
| spring-boot-starter-data-couchbase-reactive |使用Couchbase面向文档的数据库和Spring Data Couchbase Reactive的入门者|
| spring-boot-starter-data-elasticsearch | 使用Elasticsearch搜索和分析引擎以及Spring Data Elasticsearch的初学者 |
| spring-boot-starter-data-jdbc | 使用Spring数据JDBC的入门者 |
| spring-boot-starter-data-jpa | 使用Spring Data JPA和Hibernate的初学者 |
| spring-boot-starter-data-ldap | 使用Spring数据LDAP的入门者 |
| spring-boot-starter-data-mongodb | 使用MongoDB面向文档的数据库和Spring Data MongoDB的初学者 |
| spring-boot-starter-data-mongodb-reactive | 使用MongoDB面向文档的数据库和Spring Data MongoDB Reactive的入门者 |
| spring-boot-starter-data-neo4j | 使用Neo4j图形数据库和Spring数据Neo4j的入门者 |
| spring-boot-starter-data-redis | 使用Spring数据Redis和Lettuce客户端使用Redis键值数据存储的入门者 |
| spring-boot-starter-data-redis-reactive | 使用Redis数据Redis被动和Lettuce客户端的Redis键值数据存储的入门者 |
| spring-boot-starter-data-rest | 使用Spring Data REST通过REST公开Spring数据存储库的入门者 |
| spring-boot-starter-data-solr | 使用带有Spring Data Solr的Apache Solr搜索平台的初学者 |
| spring-boot-starter-freemarker | 使用FreeMarker视图构建MVC Web应用程序的入门者 |
| spring-boot-starter-groovy-templates | 使用Groovy模板视图构建MVC Web应用程序的入门者 |
| spring-boot-starter-hateoas | 使用Spring MVC和Spring HATEOAS构建基于超媒体的RESTful Web应用程序的入门者 |
| spring-boot-starter-integration | 使用Spring Integration的入门者 |
| spring-boot-starter-jdbc | 将JDBC与HikariCP连接池一起使用的入门者 |
| spring-boot-starter-jersey | 使用JAX-RS和Jersey构建RESTful Web应用程序的入门者。替代spring-boot-starter-web |
| spring-boot-starter-jooq | 使用jOOQ访问SQL数据库的初学者。替代spring-boot-starter-data-jpa或spring-boot-starter-jdbc |
| spring-boot-starter-json | 阅读和写作json的初学者 |
| spring-boot-starter-jta-atomikos | 使用Atomikos进行JTA交易的入门者 |
| spring-boot-starter-jta-bitronix | 使用Bitronix进行JTA事务的入门者 |
| spring-boot-starter-mail | 使用Java Mail和Spring Framework的电子邮件发送支持的入门者 |
| spring-boot-starter-mustache | 使用Mustache视图构建Web应用程序的入门者 |
| spring-boot-starter-oauth2-client | 使用Spring安全性OAuth2 / OpenID Connect客户端功能的入门级产品 |
| spring-boot-starter-oauth2-resource-server | 使用Spring安全性OAuth2资源服务器功能的入门者 |
| spring-boot-starter-quartz | 使用Quartz调度程序的入门者 |
| spring-boot-starter-security | 使用Spring安全性的入门者 |
| spring-boot-starter-test | 用于测试包含JUnit，Hamcrest和Mockito等库的Spring Boot应用程序的入门者 |
| spring-boot-starter-thymeleaf | 使用Thymeleaf视图构建MVC Web应用程序的入门者 |
| spring-boot-starter-validation | 使用Java Bean验证与Hibernate Validator的初学者 |
| spring-boot-starter-web | 使用Spring MVC构建Web（包括RESTful）应用程序的入门者。使用Tomcat作为默认嵌入式容器 |
| spring-boot-starter-web-services | 使用Spring Web服务的入门者 |
| spring-boot-starter-webflux | 使用Spring Framework的Reactive Web支持构建WebFlux应用程序的初学者 |
| spring-boot-starter-websocket | 使用Spring Framework的WebSocket支持构建WebSocket应用程序的入门者 |
| spring-boot-starter-actuator | 使用Spring Boot的Actuator的启动器，它提供生产就绪功能，帮助您监控和管理您的应用程序 |
| spring-boot-starter-jetty | 使用Jetty作为嵌入式servlet容器的入门。替代spring-boot-starter-tomcat |
| spring-boot-starter-log4j2 | 使用Log4j2进行日志记录的入门。替代spring-boot-starter-logging |
| spring-boot-starter-logging | 使用Logback进行日志记录的入门。默认日志启动器 |
| spring-boot-starter-reactor-netty | 使用Reactor Netty作为嵌入式响应式HTTP服务器的入门者 |
| spring-boot-starter-tomcat | 使用Tomcat作为嵌入式servlet容器的入门者。使用的默认servlet容器启动器spring-boot-starter-web |
| spring-boot-starter-undertow | 使用Undertow作为嵌入式servlet容器的入门者。替代spring-boot-starter-tomcat |


