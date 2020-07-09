# jta-atomikos
```text
    通常，JDBC事务就可以解决数据的一致性等问题，鉴于他用法相对简单，所以很多人关于Java中的事务只知道有JDBC事务，或者有人知道框架中的事务
（比如Hibernate、Spring）等。但是，由于JDBC无法实现分布式事务，而如今的分布式场景越来越多，所以，JTA事务就应运而生。
    如果，你在工作中没有遇到JDBC事务无法解决的场景，那么只能说你做的项目还都太小。拿电商网站来说，我们一般把一个电商网站横向拆分成商品模块、
订单模块、购物车模块、消息模块、支付模块等。然后我们把不同的模块部署到不同的机器上，各个模块之间通过远程服务调用(RPC)等方式进行通信。
以一个分布式的系统对外提供服务。
    JTA事务比JDBC事务更强大。一个JTA事务可以有多个参与者，而一个JDBC事务则被限定在一个单一的数据库连接。下列任一个Java平台的组件都可以参与到
一个JTA事务中：JDBC连接、JDO PersistenceManager 对象、JMS 队列、JMS 主题、企业JavaBeans（EJB）、一个用J2EE Connector Architecture 
规范编译的资源分配器。

JTA的优点很明显，就是提供了分布式事务的解决方案，严格的ACID。但是，标准的JTA方式的事务管理在日常开发中并不常用，因为他有很多缺点:

实现复杂,通常情况下，JTA UserTransaction需要从JNDI获取。这意味着，如果我们使用JTA，就需要同时使用JTA和JNDI;
JTA本身就是个笨重的API;
通常JTA只能在应用服务器环境下使用，因此使用JTA会限制代码的复用性。

https://blog.csdn.net/u014201191/article/details/90713546

实战解决多数据源事务问题(使用JTA处理分布式事务)
    Spring Boot通过Atomkos或Bitronix的内嵌事务管理器支持跨多个XA资源的分布式JTA事务，当部署到恰当的J2EE应用服务器时也会支持JTA事务。
    当发现JTA环境时，Spring Boot将使用Spring的 JtaTransactionManager 来管理事务。自动配置的JMS，DataSource和JPA　beans将被升级以支持XA事务。  
 可以使用标准的Spring idioms，比如 @Transactional ，来参与到一个分布式事务中。如果处于JTA环境，但仍想使用本地事务，
你可以将 spring.jta.enabled 属性设置为 false 来禁用JTA自动配置功能。

使用Atomikos事务管理器
    Atomikos是一个非常流行的开源事务管理器，并且可以嵌入到Spring Boot应用中。可以使用 spring-boot-starter-jta-atomikos Starter去获取正确的Atomikos库。
Spring Boot会自动配置Atomikos，并将合适的 depends-on 应用到Spring Beans上，确保它们以正确的顺序启动和关闭。
    默认情况下，Atomikos事务日志将被记录在应用home目录（应用jar文件放置的目录）下的 transaction-logs 文件夹中。可以在 application.properties 文件中
通过设置 spring.jta.log-dir 属性来定义该目录，以 spring.jta.atomikos.properties 开头的属性能用来定义Atomikos的 UserTransactionServiceIml 实现，具体参考AtomikosProperties javadoc。
    注 为了确保多个事务管理器能够安全地和相应的资源管理器配合，每个Atomikos实例必须设置一个唯一的ID。默认情况下，该ID是Atomikos实例运行的机器上的IP地址。
为了确保生产环境中该ID的唯一性，需要为应用的每个实例设置不同的 spring.jta.transaction-manager-id 属性值。





```