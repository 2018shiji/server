package com.module.server;

import com.module.server.config.JMXServerConfig;
import com.module.server.util.MyBeanContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        MyBeanContext.getBean(JMXServerConfig.class).startJMXServer();
    }

}
