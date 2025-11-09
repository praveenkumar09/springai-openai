package com.praveen.springai_openai.config;

import com.praveen.springai_openai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.prompt.ChatOptions;
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

    @Bean("chatMemoryChatClient")
    public ChatClient chatMemoryChatClient(
            ChatClient.Builder chatClientBuilder,
            ChatMemory chatMemory
    ) {
        MessageChatMemoryAdvisor cmAdvisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();
        SimpleLoggerAdvisor simpleLoggerAdvisor = new SimpleLoggerAdvisor();
        return chatClientBuilder
                .defaultAdvisors(List.of(
                        cmAdvisor,
                        simpleLoggerAdvisor
                ))
                .build();
    }

}
