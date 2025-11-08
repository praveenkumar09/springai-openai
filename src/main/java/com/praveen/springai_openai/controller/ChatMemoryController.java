package com.praveen.springai_openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memory")
public class ChatMemoryController {

    private final ChatClient chatMemoryChatClient;

    public ChatMemoryController(ChatClient chatMemoryChatClient) {
        this.chatMemoryChatClient = chatMemoryChatClient;
    }

    @RequestMapping("/chat")
    public String chat(
            @RequestParam("message") String message
    ) {
        return chatMemoryChatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }

}
