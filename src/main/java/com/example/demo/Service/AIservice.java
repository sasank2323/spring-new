package com.example.demo.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIservice {

   private final ChatClient chatClient;

    public String getAIResponse(String userInput) {
        return chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

}
