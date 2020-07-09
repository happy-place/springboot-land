package com.bigdata.boot.chapter03.example1.jms;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * @ClassName AMQueue
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/6/10 14:27
 * @Version 1.0
 **/

@Configuration
public class SpringConfig {

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("sample.queue");
    }

}
