package com.praveen.springai_openai.config;

import com.praveen.springai_openai.tools.HelpDeskTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/tools")
public class HelpDeskController {

    private final ChatClient helpDeskChatClient;

    private final HelpDeskTools helpDeskTools;

    public HelpDeskController(ChatClient helpDeskChatClient, HelpDeskTools helpDeskTools) {
        this.helpDeskChatClient = helpDeskChatClient;
        this.helpDeskTools = helpDeskTools;
    }

    @GetMapping("/help-desk")
    public ResponseEntity<String> helpDesk(
            @RequestHeader("username") String username,
            @RequestParam("message") String message
    ){
        String answer = helpDeskChatClient
                .prompt()
                .advisors(advisorSpec ->
                        advisorSpec.param(CONVERSATION_ID, username))
                .user(message)
                .tools(helpDeskTools)
                .toolContext(Map.of("username", username))
                .call()
                .content();
        return ResponseEntity.ok(answer);
    }


}
