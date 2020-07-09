package com.bigdata.boot.chapter60.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName Ports
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/7/2 08:48
 * @Version 1.0
 **/

@Configuration
@Slf4j
public class Ports implements ApplicationListener<WebServerInitializedEvent> {

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        Service service = ((TomcatWebServer) event.getWebServer()).getTomcat()
                .getService();
        for (Connector connector : service.findConnectors()) {
            if (connector.getSecure()) {
                log.info("https port: {}",connector.getLocalPort());
            }
            else {
                log.info("http port: {}",connector.getLocalPort());
            }
        }
    }

}

