# actuator-custom-security

## 简介
```text
Endpoint: Spring Boot的Endpoint主要是用来监控应用服务的运行状况，并集成在MVC中提供查看接口。内置的Endpoint比如HealthEndpoint会监控dist和db的状况
Security: spring 权限控制模块。
```

## 普通MVC
### 启动类 Application
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleActuatorCustomSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleActuatorCustomSecurityApplication.class, args);
	}

}
```

### 请求控制器 Controller
```java
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/example")
public class ExampleController {

	@GetMapping("/my-home")
	public String home(Map<String, Object> model) {
		model.put("message", "Hello World");
		model.put("title", "Hello Home");
		model.put("date", new Date());
		return "home"; // 默认取 resources/templates 下匹配 home.ftl
	}

	@RequestMapping("/foo")
	public String foo() {
	    // 出现异常自动取 resources/templates 下匹配 error.ftl 渲染异常
		throw new RuntimeException("Expected exception in controller");
	}

}


// GET http://localhost:8080/example/my-home
// GET http://localhost:8080/example/foo
```

## Endpoint MVC
### 启动器Application(通用)
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleActuatorCustomSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleActuatorCustomSecurityApplication.class, args);
	}

}
```
### 控制器 ControllerEndpoint
```java
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@RestControllerEndpoint(id = "example-rest") // 此处为 endpoint 名称不能带'/'
public class ExampleRestControllerEndpoint {

	@GetMapping("/echo")
	public ResponseEntity<String> echo(@RequestParam("text") String text) {
		return ResponseEntity.ok().header("echo", text).body(text); 
	}

}

// GET http://localhost:8080/actuator/example-rest/echo?text=123
```

## security 权限管理
```http request
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @SuppressWarnings("deprecation")
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
        		// user:password 为 ROLE_USER 角色
			User.withDefaultPasswordEncoder().username("user").password("password").authorities("ROLE_USER").build(),
				// beans:beans 为 ROLE_BEANS 角色
			User.withDefaultPasswordEncoder().username("beans").password("beans").authorities("ROLE_BEANS").build(),
				// admin:admin 为 ROLE_ACTUATOR、ROLE_USER 角色
			User.withDefaultPasswordEncoder().username("admin").password("admin").authorities("ROLE_ACTUATOR", "ROLE_USER").build()
		);
    }

   
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 访问 /actuator/beans 需要 BEANS 角色，即对应上面的 ROLE_BEANS
                .mvcMatchers("/actuator/beans").hasRole("BEANS")
                // 访问 /actuator/health 和 /actuator/info 直接放行
                .requestMatchers(EndpointRequest.to("health", "info","env")).permitAll()
                // 排除MappingsEndpoint以外，所有 EndpointRequest 都需要 ACTUATOR 角色
                .requestMatchers(EndpointRequest.toAnyEndpoint().excluding(MappingsEndpoint.class)).hasRole("ACTUATOR")
                // 公共路径下的静态资源请求直接放行
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // 请求 /foo 直接放行
                .antMatchers("/example/foo").permitAll()
                // 除上述 角色声明外，其余请求均需要 USER 角色
                .antMatchers("/example/**").hasRole("USER")
                .and()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()) // 运行跨域
                .and()
                .httpBasic(); // Basic授权
    }

}
```



