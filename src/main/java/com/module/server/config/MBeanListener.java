package com.module.server.config;

import org.springframework.stereotype.Component;

import javax.management.Notification;
import javax.management.NotificationListener;

@Component
public class MBeanListener implements NotificationListener {

    @Override
    public void handleNotification(Notification notification, Object handback) {
        System.out.println(notification.getMessage());
        System.out.println(handback);
    }

}
