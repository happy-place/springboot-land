# cassandra
```text
    Cassandra是一套开源分布式NoSQL数据库系统。它最初由Facebook开发，用于储存收件箱等简单格式数据，集GoogleBigTable的数据模型与Amazon Dynamo
的完全分布式的架构于一身Facebook于2008将 Cassandra 开源，此后，由于Cassandra良好的可扩展性，被Digg、Twitter等知名Web 2.0网站所采纳，
成为了一种流行的分布式结构化数据存储方案。
    Cassandra是一个混合型的非关系的数据库，类似于Google的BigTable。其主要功能比Dynamo （分布式的Key-Value存储系统）更丰富，
但支持度却不如文档存储MongoDB（介于关系数据库和非关系数据库之间的开源产品，是非关系数据库当中功能最丰富，最像关系数据库的。支持的数据结构非常松散，
是类似json的bjson格式，因此可以存储比较复杂的数据类型）。Cassandra最初由Facebook开发，后转变成了开源项目。它是一个网络社交云计算方面理想的数据库。
以Amazon专有的完全分布式的Dynamo为基础，结合了Google BigTable基于列族（Column Family）的数据模型。P2P去中心化的存储。很多方面都可以称之为Dynamo 2.0。
```
##  项目结构
### model
```java
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Customer {

	@PrimaryKey
	private UUID id;

	@Column("first_name")
	private String firstName;

	@Column("last_name")
	private String lastName;

	public Customer() {}

	public Customer(UUID id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s']", this.id,
				this.firstName, this.lastName);
	}

}
```
### mapper
```java
import java.util.List;

import com.bigdata.boot.chapter13.model.Customer;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CustomerRepository extends CassandraRepository<Customer,String> {

	@Query("Select * from customer where first_name=?0") // 必须是 ?0
	Customer findByFirstName(String firstName);

	@Query("Select * from customer where last_name=?0")
	List<Customer> findByLastName(String lastName);

}
```
### 内嵌cassandra
```java
import com.datastax.driver.core.Session;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.FileCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;

@Configuration
public class CassandraConfiguration extends AbstractCassandraConfiguration {


    private Logger logger = LoggerFactory.getLogger(CassandraConfiguration.class);

    @Value("${spring.data.cassandra.keyspace-name}")
    public String keyspace;

    // 生产环境
//    @Bean
//    @Override
//    public CassandraClusterFactoryBean cluster() {
//        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
//        cluster.setContactPoints("localhost");
//        cluster.setPort(9042);
//        return cluster;
//    }
    
    // 测试环境
    @Bean
    @Override
    public CassandraClusterFactoryBean cluster() {
        try {
            EmbeddedCassandraServerHelper.startEmbeddedCassandra(EmbeddedCassandraServerHelper.DEFAULT_CASSANDRA_YML_FILE, 1000000L);
            EmbeddedCassandraServerHelper.getCluster().getConfiguration().getSocketOptions().setReadTimeoutMillis(1000000);
            Session session = EmbeddedCassandraServerHelper.getSession();
            CQLDataLoader dataLoader = new CQLDataLoader(session);
            String initScript = getClass().getClassLoader().getResource("setup.cql").getPath();
            dataLoader.load(new FileCQLDataSet(initScript, true, true, getKeyspaceName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Can't start Embedded Cassandra", e);
        }
        return super.cluster();
    }


    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    protected int getPort() {
        return 9142;
    }

}
```
### 配置信息
```properties
# application.properties
spring.data.cassandra.keyspace-name=mykeyspace
```
```cassandraql
// setup.cql
CREATE TABLE customer (id TimeUUID PRIMARY KEY, firstname text, lastname text);
CREATE INDEX customerfistnameindex ON customer (firstname);
CREATE INDEX customersecondnameindex ON customer (lastname);
```

### 启动类
```java
import com.bigdata.boot.chapter13.model.Customer;
import com.bigdata.boot.chapter13.repo.CustomerRepository;
import com.datastax.driver.core.utils.UUIDs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories(basePackages = "com.bigdata.boot.chapter13.repo")
public class SampleCassandraApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;

	@Override
	public void run(String... args) throws Exception {
		this.repository.deleteAll();

		// save a couple of customers
		this.repository.save(new Customer(UUIDs.timeBased(), "Alice", "Smith"));
		this.repository.save(new Customer(UUIDs.timeBased(), "Bob", "Smith"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : this.repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(this.repository.findByFirstName("Alice"));

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Customer customer : this.repository.findByLastName("Smith")) {
			System.out.println(customer);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleCassandraApplication.class, args);
	}

}
```