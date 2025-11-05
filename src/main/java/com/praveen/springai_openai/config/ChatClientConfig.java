package com.praveen.springai_openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
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
