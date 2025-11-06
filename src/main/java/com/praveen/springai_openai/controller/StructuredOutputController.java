package com.praveen.springai_openai.controller;

import com.praveen.springai_openai.record.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/structured")
public class StructuredOutputController {

    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("chat-bean")
    public ResponseEntity<CountryCities> chatBean(
            @RequestParam("message") String message
    ) {
        CountryCities entity = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(CountryCities.class);
        return ResponseEntity.ok(entity);
    }
}
