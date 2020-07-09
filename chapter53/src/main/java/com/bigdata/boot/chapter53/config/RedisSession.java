package com.bigdata.boot.chapter53.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@Configuration
@EnableRedisHttpSession(redisNamespace = "mysession",maxInactiveIntervalInSeconds = 60*60*24)
public class RedisSession {

}