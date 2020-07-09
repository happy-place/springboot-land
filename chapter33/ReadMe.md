# JPA
```text
base on habernate
use freemaker for web

    JPA （The Java Persistence API）是用于访问，持久化和管理 Java 对象/类与关系型数据库之间的数据交互的 Java 规范。JPA 被定义为EJB 
（Enterprise JavaBeans） 3.0规范的一部分，作为 EJB 2 CMP 实体 Bean 规范的替代。
    注意，JPA 只是一个标准，只定义了一系列接口，而没有具体的实现。很多企业级框架提供了对 JPA 的实现，如 Spring 。因此 Spring 本身与 JPA 无关，
只是提供了对 JPA 的支持，因此在 Spring 中你也会看到很多注解都是属于 javax.persistence 包的。
    JPA 允许 POJO（Plain Old Java Objects）轻松地持久化，而不需要类来实现 EJB 2 CM P规范所需的任何接口或方法。 JPA 还允许通过注解或 XML 
定义对象的关系映射，定义 Java 类如何映射到关系数据库表。 JPA 还定义了一个运行时 EntityManager API，用于处理对象的查询和管理事务。 同时，JPA 定义了对象级查询语言 JPQL，以允许从数据库中查询对象，实现了对数据库的解耦合，提高了程序的可移植性，而不具体依赖某一底层数据库。
```