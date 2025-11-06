package com.praveen.springai_openai.config;

import com.praveen.springai_openai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultAdvisors(List.of(
                        new SimpleLoggerAdvisor(),
                        new TokenUsageAuditAdvisor()
                ))
                .defaultSystem("""
                        You are an internal HR assistant. Your role is to\s
                        help employees with their questions related to their\s
                        HR Policies, such as leave policies, benefits and code\s
                        of conduct. If a user asks for help with anything outside\s
                        of these topics, kindly inform them you can only assist\s
                        with queries related to HR policies.
                        """)
                .build();
    }
}
