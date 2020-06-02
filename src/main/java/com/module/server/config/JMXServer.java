package com.module.server.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * RMI协议的连接参数配置
 */
@Data
@Component("jmxServer")
public class JMXServer {
    protected String protocol;
    protected String ip;
    protected int port;

    protected String connAddress;

    private String service;

    public JMXServer(){
        protocol = "rmi";
        ip = "127.0.0.1";
        port = 9999;
        service = "jmx";
        connAddress = "service:jmx:rmi:///jndi/rmi://" + ip + ":" + port + "/" + service;
    }

    public String getConnAddress() {
        return "service:jmx:rmi:///jndi/rmi://" + ip + ":" + port + "/" + service;
    }

}
