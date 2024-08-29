package com.programandoenjava.java_bot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/chats")
@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping
    public String postChat(@RequestBody ChatRequest chatRequest) {
        return this.chatClient.prompt()
                .user(chatRequest.message())
                .call()
                .content();
    }
}
