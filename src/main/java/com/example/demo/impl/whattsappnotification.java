package com.example.demo.impl;

import com.example.demo.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class whattsappnotification implements NotificationService {

    public void sendNotification(String message)
    {
        System.out.println("Sending WhatsApp notification: " + message);
    }
    public static void main(String[] args) {

    }
}
