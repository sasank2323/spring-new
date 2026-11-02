package com.example.demo;

import com.example.demo.Entity.User;
import com.example.demo.Service.JwtService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled
public class Testing {

    @Autowired
    private JwtService jwtService;

    @Test
    public void testExample() {
        // Example test case
        System.out.println("Running a test case...");
        // Add assertions or test logic here
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        String token = jwtService.generateToken(user);
        // Do something with the generated token, e.g., assert its validity or properties
        Long userId = jwtService.getUserIdFromToken(token);
        // Assert that the extracted userId matches the original user's ID
        System.out.println("Generated Token: " + token);
        System.out.println("Extracted User ID: " + userId);
    }


}
