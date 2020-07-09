package com.bigdata.boot.chapter07.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;

//@Configuration
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
                .antMatchers("/foo").permitAll()
                // 除上述 角色声明外，其余请求均需要 USER 角色
                .antMatchers("/**").hasRole("USER")
                .and()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()) // 运行跨域
                .and()
                .httpBasic(); // Basic授权
    }

}