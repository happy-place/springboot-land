package com.bigdata.boot.chapter36.component;

import javax.jms.ConnectionFactory;
import javax.naming.NamingException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import java.util.Properties;


@Configuration
@Slf4j
public class EmbeddedTomcat {

    @Value("${jndi.db.resource.name}")
    private String resourceName;

    @Value("${jndi.db.name}")
    private String jndiName;

    @Value("${jndi.db.driver}")
    private String driverName;

    @Value("${jndi.db.url}")
    private String dbUrl;
//    private String dbUrl="jdbc:h2:~/software/idea_proj/springboot-land/chapter36/db/h2";

    @Value("${jndi.db.username}")
    private String username;

    @Value("${jndi.db.password}")
    private String password;

    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        log.info("Initializing tomcat factory... ");
        return new TomcatServletWebServerFactory() {

            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();
                return new TomcatWebServer(tomcat, getPort() >= 0);
            }

            private void registJdbcResource(Context context){
                ContextResource resource = new ContextResource();
                resource.setName(resourceName);
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName",driverName);
                resource.setProperty("url", dbUrl);
                resource.setProperty("username", username);
                resource.setProperty("password", password);
                context.getNamingResources().addResource(resource);
            }

            @Override
            protected void postProcessContext(Context context) {
                log.info("initializing tomcat factory JDNI ... ");
                registJdbcResource(context);
            }
        };
    }

    @Bean(destroyMethod="")
    public DataSource jndiJdbcDataSource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName(jndiName);
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(true);
        bean.afterPropertiesSet();
        return (DataSource)bean.getObject();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws IllegalArgumentException, NamingException {
        return new JdbcTemplate(jndiJdbcDataSource());
    }



}