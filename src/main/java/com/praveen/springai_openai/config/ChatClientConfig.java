package com.praveen.springai_openai.config;

import com.praveen.springai_openai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.function.Supplier;

@Configuration
public class ChatClientConfig {

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource systemPromptTemplate;

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultOptions(chatOptions().get())
                .defaultAdvisors(List.of(
                        new SimpleLoggerAdvisor(),
                        new TokenUsageAuditAdvisor()
                ))
                .defaultSystem(systemPromptTemplate)
                .build();
    }

    public Supplier<ChatOptions> chatOptions() {
        return () -> ChatOptions.builder()
                //.model("gpt-4.1-mini")
                .temperature(0.8)
                //.maxTokens(10)
                //.stopSequences(List.of("END"))
                .build();
    }
}
