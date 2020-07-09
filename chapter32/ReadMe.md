# jooq
```text
    对标 mybatis-plus 插件，自动化orm插件
    JOOQ是基于Java访问关系型数据库的工具包，它具有轻量、简单、并且足够灵活的特点，通过JOOQ我们可以轻松的使用Java面向对象的语法来实现各种
复杂的SQL。相比于传统ORM框架，如Hibernate、Mybatis来说，JOOQ汲取了即汲取了它们操作数据的简单性和安全性、同时也保留了原生SQL的灵活性，
从某种程度上说JOOQ更像是介于ORM和JDBC的中间层。
```

## 配置pom
```text
略
```

### 编写初始化sql 
```text
略
```

###
```text
编写生成器策略 DefaultGeneratorStrategy
```

### 动态生成orm
```shell script
generate 对应 pom.xml 中名称为generate的<profile>
mvn clean generate-sources -Pgenerate
```

### 测试样例
```text
JooqExamples
```