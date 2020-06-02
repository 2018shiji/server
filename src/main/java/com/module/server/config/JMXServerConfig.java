package com.module.server.config;

import com.module.server.mBeanInfo.ServerAttribute;
import com.module.server.mBeanInfo.Notify;
import com.module.server.util.IMBean;
import com.module.server.util.MyBeanContext;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.util.*;

/**
 * Config server by JMX
 * 通过Java Manage Extend配置服务端连接参数
 */
@Setter
@Component
public class JMXServerConfig {

    @Autowired private JMXServer JMXServer;

    @Autowired private MBeanListener listener;

    public void startJMXServer() {
        try{
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

            Set<Class<?>> classes = MyBeanContext.getPackageClasses("com.module.server.mBeanInfo");
            classes.stream().filter(item -> item.isAnnotationPresent(IMBean.class));
            for(Class clazz : classes) {
                Object mBean = MyBeanContext.getBean(clazz);
                ObjectName name = new ObjectName("jmxBean:name=" + mBean.getClass().getSimpleName());
                mBeanServer.registerMBean(mBean, name);
            }


            LocateRegistry.createRegistry(JMXServer.getPort());
            JMXServiceURL jmxServiceURL = new JMXServiceURL(JMXServer.getConnAddress());
            System.out.println(JMXServer.getConnAddress());

            JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(jmxServiceURL, null, mBeanServer);
            System.out.println(cs.getMBeanServer().getMBeanCount());
            cs.start();


//            notify.addNotificationListener(listener, null, "i am here");


        } catch (Exception e){
            e.printStackTrace();

        }

    }


}
