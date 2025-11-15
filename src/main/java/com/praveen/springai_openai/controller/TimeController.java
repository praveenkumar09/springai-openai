package com.praveen.springai_openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/time")
public class TimeController {

    private final ChatClient timeChatClient;


    public TimeController(ChatClient timeChatClient) {
        this.timeChatClient = timeChatClient;
    }

    @RequestMapping("/chat")
    public String chat(
            @RequestHeader("username") String username,
            @RequestParam("message") String message
    ){
        return timeChatClient
                .prompt()
                .user(message)
                .advisors(advisorSpec ->
                        advisorSpec.param(CONVERSATION_ID,username))
                .call()
                .content();
    }
}
