package com.module.server.mBeanInfo;

import com.module.server.util.IMBean;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@IMBean
@Component("serverMBean")
public class ServerAttribute implements ServerAttributeMBean {

    protected String protocol;
    protected String ip;
    protected int port;

    protected String connAddress;

    @Autowired
    public String getConnAddress(){
        return "server attribute";
    }

}
