package com.example.demo.impl;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Primary;
import com.example.demo.NotificationService;

@Primary
@Component
public class smsNotification implements NotificationService {

    public void sendNotification(String message)
    {
        System.out.println("Sending SMS notification: " + message);
    }
    public static void main(String[] args) {

    }
}
