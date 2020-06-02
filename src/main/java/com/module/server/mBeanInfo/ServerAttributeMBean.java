package com.module.server.mBeanInfo;

import org.springframework.stereotype.Component;

@Component
public interface ServerAttributeMBean {
    String name = "Server";

    String getProtocol();
    void setProtocol(String protocol);

    String getIp();
    void setIp(String ip);

    int getPort();
    void setPort(int port);

    String getConnAddress();
    void setConnAddress(String address);

    default String printAddress(){
        return getConnAddress();
    };

}
