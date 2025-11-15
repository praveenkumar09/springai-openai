package com.praveen.springai_openai.config;

import com.praveen.springai_openai.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.execution.ToolExecutionExceptionProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class HelpDeskChatClientConfig {

    @Value("classpath:/promptTemplates/helpDeskSystemPromptTemplate.st")
    Resource helpDeskSystemPromptTemplate;

    @Bean("helpDeskChatClient")
    public ChatClient helpDeskChatClient(
            ChatClient.Builder chatClientBuilder,
            ChatMemory chatMemory,
            TimeTools timeTools
    ) {
        MessageChatMemoryAdvisor cmAdvisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();
        SimpleLoggerAdvisor simpleLoggerAdvisor = new SimpleLoggerAdvisor();

        return chatClientBuilder
                .defaultSystem(helpDeskSystemPromptTemplate)
                .defaultTools(timeTools)
                .defaultAdvisors(List.of(
                        cmAdvisor,
                        simpleLoggerAdvisor
                ))
                .build();
    }

//    @Bean
//    public ToolExecutionExceptionProcessor toolExecutionExceptionProcessor(){
//        return new DefaultToolExecutionExceptionProcessor(true);
//    }

}
