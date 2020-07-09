package com.bigdata.boot.chapter70.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @ClassName WebConfig
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/7/8 06:56
 * @Version 1.0
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/access").setViewName("access");
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration
    protected static class AuthenticationSecurity {

        @SuppressWarnings("deprecation")
        @Bean
        public InMemoryUserDetailsManager inMemoryUserDetailsManager() throws Exception {
            return new InMemoryUserDetailsManager(
                    User.withDefaultPasswordEncoder().username("admin").password("admin")
                            .roles("ADMIN", "USER", "ACTUATOR").build(),
                    User.withDefaultPasswordEncoder().username("user").password("user")
                            .roles("USER").build());
        }

    }

    @Configuration
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.authorizeRequests()
                    .antMatchers("/css/**").permitAll()
                    .anyRequest().fullyAuthenticated()
                    .and()
                    .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                    .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .and()
                    .exceptionHandling().accessDeniedPage("/access?error");
            // @formatter:on
        }

    }

    @Configuration
    @Order(1)
    protected static class ActuatorSecurity extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic();
            // @formatter:on
        }

    }
}
