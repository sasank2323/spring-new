package com.example.demo.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIconfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("You are a helpful assistant for my app. Answer in short sentences.")
                .defaultOptions(OllamaChatOptions.builder().temperature(0.7))
                .build();
    }
}
