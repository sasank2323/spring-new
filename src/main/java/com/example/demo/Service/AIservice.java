package com.example.demo.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIservice {

    @Value("classpath:sample.pdf")
    private Resource samplePdf;

   private final ChatClient chatClient;
   private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public void storeEmbedding(String text) {

        Document document = new Document(text);
        vectorStore.add(List.of(document));
    }

    public void ingestData() {
        List<Document> documents = List.of(
                // ---- Existing 5 ----
                new Document("Spring AI is a framework for building AI applications in Java.",
                        Map.of("id", "doc-1", "source", "demo", "category", "framework")),
                new Document("Vector stores allow semantic search over embedded documents.",
                        Map.of("id", "doc-2", "source", "demo", "category", "concept")),
                new Document("Embeddings convert text into numerical vector representations.",
                        Map.of("id", "doc-3", "source", "demo", "category", "concept")),
                new Document("RAG combines retrieval with LLM generation for better answers.",
                        Map.of("id", "doc-4", "source", "demo", "category", "pattern")),
                new Document("Batching helps ingest large datasets efficiently.",
                        Map.of("id", "doc-5", "source", "demo", "category", "pattern")),

                // ---- Additional documents ----
                new Document("ChatClient is Spring AI's fluent API for chat model interactions.",
                        Map.of("id", "doc-6", "source", "demo", "category", "framework")),
                new Document("Prompt templates let you inject variables into prompts dynamically.",
                        Map.of("id", "doc-7", "source", "demo", "category", "framework")),
                new Document("Structured output converts LLM responses directly into Java records.",
                        Map.of("id", "doc-8", "source", "demo", "category", "pattern")),
                new Document("Function calling enables LLMs to invoke your application code.",
                        Map.of("id", "doc-9", "source", "demo", "category", "pattern")),
                new Document("TokenTextSplitter chunks large texts with configurable overlap.",
                        Map.of("id", "doc-10", "source", "demo", "category", "tooling")),
                new Document("Cosine similarity measures the angle between two embedding vectors.",
                        Map.of("id", "doc-11", "source", "demo", "category", "concept")),
                new Document("Top-K retrieval returns the K most similar documents to a query.",
                        Map.of("id", "doc-12", "source", "demo", "category", "concept")),
                new Document("Similarity threshold filters out low-relevance search results.",
                        Map.of("id", "doc-13", "source", "demo", "category", "concept")),
                new Document("Metadata filters narrow vector searches by document attributes.",
                        Map.of("id", "doc-14", "source", "demo", "category", "pattern")),
                new Document("PgVector, Chroma, and Pinecone are popular vector store backends.",
                        Map.of("id", "doc-15", "source", "demo", "category", "tooling"))
        );

        vectorStore.add(documents);
        System.out.println("Ingested " + documents.size() + " documents");
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

    public String getAIResponseWithStructuredOutput(String userInput) {
        String s= """
                hi this is system prompt '{userInput}' and you are a helpful assistant for my app. Answer in short sentences. {document} is the context for your answer. Use it to answer the question. If you don't know the answer, say "I don't know".
                """;
        // system prompt is present in defaultSystem and userInput is present in user and context is present in similaritySearch
        List<Document> documnets=vectorStore.similaritySearch(SearchRequest.builder().
                        query(userInput).
                        filterExpression("category == 'concept'").build());
        // rag is present in similaritySearch geeting data from vectorStore and context is present in similaritySearch and userInput is present in user
        String context=documnets.stream().map(Document::getText).reduce("", (a, b) -> a + "\n" + b);
        PromptTemplate promptTemplate = new PromptTemplate(s);
        //sending userInput and context to promptTemplate to get formattedPrompt
        String formattedPrompt = promptTemplate.render(Map.of("userInput", userInput, "context", context));
        return chatClient.prompt().user(formattedPrompt).call().content();

    }

    public void addPdf() {
        PagePdfDocumentReader reader =
                new PagePdfDocumentReader(samplePdf);
        List<Document> documents = reader.read();
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(1000)
                .withMinChunkSizeChars(400)
                .withMinChunkLengthToEmbed(10)
                .withMaxNumChunks(5000)
                .withKeepSeparator(true)
                .build();
        List<Document> splitDocuments = splitter.split(documents);
        vectorStore.add(splitDocuments);
        System.out.println("Ingested " + splitDocuments.size() + " documents from PDF");
    }

}
