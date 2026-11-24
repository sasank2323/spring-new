package com.example.demo.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIservice {

   private final ChatClient chatClient;
   private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public void storeEmbedding(String text) {

        Document document = new Document(text);
        vectorStore.add(List.of(document));
    }

    public void ingestData() {
        List<Document> documents = List.of(
                new Document("Spring AI is a framework for building AI applications in Java."),
                new Document("Vector stores allow semantic search over embedded documents."),
                new Document("Embeddings convert text into numerical vector representations."),
                new Document("RAG combines retrieval with LLM generation for better answers."),
                new Document("Batching helps ingest large datasets efficiently.")
        );

        vectorStore.add(documents);
    }


    public List<Document> searchSimilarDocuments(String query, int topK) {
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(topK)
                        .build()
        );
    }

   public float[] getEmbedding(String text) {
        return embeddingModel.embed(text);
    }

//+


    public String getAIResponse(String userInput) {
        String s= """
                hi this is system prompt '{userInput}' and you are a helpful assistant for my app. Answer in short sentences.
                """;
        PromptTemplate promptTemplate = new PromptTemplate(s);
        String formattedPrompt = promptTemplate.render(Map.of("userInput", userInput));
        return chatClient.prompt().user(formattedPrompt).call().content();
    }

}
