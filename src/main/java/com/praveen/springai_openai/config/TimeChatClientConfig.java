package com.praveen.springai_openai.config;

import com.praveen.springai_openai.rag.PIIMaskingDocumentPostProcessor;
import com.praveen.springai_openai.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TimeChatClientConfig {


    @Bean("timeChatClient")
    public ChatClient timeChatClient(
            ChatClient.Builder chatClientBuilder,
            ChatMemory chatMemory,
            TimeTools timeTools
    ) {
        MessageChatMemoryAdvisor cmAdvisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();
        SimpleLoggerAdvisor simpleLoggerAdvisor = new SimpleLoggerAdvisor();

        return chatClientBuilder
                .defaultTools(timeTools)
                .defaultAdvisors(List.of(
                        cmAdvisor,
                        simpleLoggerAdvisor
                ))
                .build();
    }

}
