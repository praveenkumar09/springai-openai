package com.praveen.springai_openai.config;

import com.praveen.springai_openai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AudioTextChatClientConfig {

    @Bean
    public ChatClient audioTextChatClient(ChatClient.Builder chatClientBuilder){
        return chatClientBuilder
                .defaultAdvisors(
                        List.of(
                                new TokenUsageAuditAdvisor(),
                                new SimpleLoggerAdvisor()
                        )
                )
                .build();
    }
}
