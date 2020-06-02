package com.module.server;

import com.module.server.config.JMXServerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    JMXServerConfig JMXServerConfig;

    @Test
    void contextLoads() {
        JMXServerConfig.startJMXServer();
    }


}
