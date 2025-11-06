package com.praveen.springai_openai.controller;

import com.praveen.springai_openai.record.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @GetMapping("chat-list")
    public ResponseEntity<List<String>> chatList(
            @RequestParam("message") String message
    ) {
        List<String> entity = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ListOutputConverter());
        return ResponseEntity.ok(entity);
    }

    @GetMapping("chat-map")
    public ResponseEntity<Map<String,Object>> chatMap(
            @RequestParam("message") String message
    ) {
        Map<String,Object> entity = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new MapOutputConverter());
        return ResponseEntity.ok(entity);
    }

    @GetMapping("chat-bean-list")
    public ResponseEntity<List<CountryCities>> chatBeanList(
            @RequestParam("message") String message
    ) {
        List<CountryCities> entity = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<CountryCities>>() {
                });
        return ResponseEntity.ok(entity);
    }
}
