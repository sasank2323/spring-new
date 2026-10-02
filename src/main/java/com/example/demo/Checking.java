package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Checking implements CommandLineRunner {

    final NotificationService notificationService;

    Checking(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void run(String... args) throws Exception {

        notificationService.sendNotification(
                "Hello, this is a test notification!"
        );
    }
}