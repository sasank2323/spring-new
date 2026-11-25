package com.example.demo;

import com.example.demo.Entity.User;
import com.example.demo.Service.AIservice;
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

    @Autowired
    private AIservice aiService;

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


    @Test
    public void testAIService() {
        String prompt = "Hello, how are you?";
        String response = aiService.getAIResponse(prompt);
        System.out.println("AI Response: " + response);
        // Add assertions or test logic here to validate the AI response
    }

    @Test
    public void testAIServiceWithDifferentPrompt() {
        String prompt = "What is the capital of France?";
        float[] response = aiService.getEmbedding(prompt);
        System.out.println("AI Response: " + java.util.Arrays.toString(response));
        // Add assertions or test logic here to validate the AI response
    }


    @Test
    public void testStoreEmbedding() {
        String text = "This is a sample text to store embedding.";
        aiService.storeEmbedding(text);
        System.out.println("Embedding stored for text: " + text);
        // Add assertions or test logic here to validate the storage of the embedding
    }

    @Test
    public void testSearchSimilarDocuments() {
        String query = "sample text";
        int topK = 5;
        var results = aiService.searchSimilarDocuments(query, topK);
        System.out.println("Search results for query: " + query);
        results.forEach(doc -> System.out.println("Document: " + doc));
        // Add assertions or test logic here to validate the search results
    }

    @Test
    public void testIngestData() {
        aiService.ingestData();
        System.out.println("Data ingested successfully.");
        // Add assertions or test logic here to validate the ingestion of data
    }

    @Test
    public void testGetAIResponseWithStructuredOutput() {
        String userInput = "Explain cosine similarity.";
        String response = aiService.getAIResponseWithStructuredOutput(userInput);
        System.out.println("AI Response with Structured Output: " + response);
        // Add assertions or test logic here to validate the AI response with structured output
    }

    @Test
    public void testAddPdf() {
        aiService.addPdf();
        System.out.println("PDF added successfully.");
        // Add assertions or test logic here to validate the addition of the PDF
    }
}
