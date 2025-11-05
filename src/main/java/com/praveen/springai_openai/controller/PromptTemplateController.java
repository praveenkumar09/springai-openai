package com.praveen.springai_openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prompt")
public class PromptTemplateController {

    private final ChatClient chatClient;

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource userPromptTemplate;

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource systemPromptTemplate;

    public PromptTemplateController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/email")
    public String chat(
            @RequestParam("customerName") String customerName,
            @RequestParam("customerMessage") String customerMessage
    ) {
        return chatClient
                .prompt()
                .system(promptSystemSpec ->
                        promptSystemSpec.text(systemPromptTemplate))
                .user(promptUserSpec -> {
                    promptUserSpec.text(userPromptTemplate)
                            .param("customerName",customerName)
                            .param("customerMessage",customerMessage);
                })
                .call()
                .content();
    }

}
