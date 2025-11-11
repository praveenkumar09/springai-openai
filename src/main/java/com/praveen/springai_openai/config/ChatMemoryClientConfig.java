package com.praveen.springai_openai.config;

import com.praveen.springai_openai.advisors.TokenUsageAuditAdvisor;
import com.praveen.springai_openai.rag.PIIMaskingDocumentPostProcessor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Supplier;

@Configuration
public class ChatMemoryClientConfig {

    @Bean
    public ChatMemory chatMemory(
            JdbcChatMemoryRepository jdbcChatMemoryRepository
    ){
        return MessageWindowChatMemory
                .builder()
                .maxMessages(10)
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .build();
    }

    @Bean
    RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(
            VectorStore vectorStore,
            ChatClient.Builder chatClientBuilder
    ){
        return RetrievalAugmentationAdvisor.builder()
                .queryTransformers(TranslationQueryTransformer
                        .builder()
                        .chatClientBuilder(chatClientBuilder.clone())
                        .targetLanguage("english")
                        .build())
                .documentRetriever(VectorStoreDocumentRetriever
                        .builder()
                        .vectorStore(vectorStore)
                        .topK(3)
                        .similarityThreshold(0.5)
                        .build())
                .documentPostProcessors(PIIMaskingDocumentPostProcessor
                        .builder())
                .build();
    }

    @Bean("chatMemoryChatClient")
    public ChatClient chatMemoryChatClient(
            ChatClient.Builder chatClientBuilder,
            ChatMemory chatMemory,
            RetrievalAugmentationAdvisor retrievalAugmentationAdvisor
    ) {
        MessageChatMemoryAdvisor cmAdvisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();
        SimpleLoggerAdvisor simpleLoggerAdvisor = new SimpleLoggerAdvisor();
        return chatClientBuilder
                .defaultAdvisors(List.of(
                        retrievalAugmentationAdvisor,
                        cmAdvisor,
                        simpleLoggerAdvisor
                ))
                .build();
    }

}
