package com.heapsteep.controller;

import com.heapsteep.model.ChatRequest;
import com.heapsteep.model.OpenAiResponse;
import com.heapsteep.service.ChatGptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatGptService chatGptService;

    public ChatController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping
    public ResponseEntity<OpenAiResponse> chat(
            @RequestBody ChatRequest chatRequest,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        OpenAiResponse response = chatGptService.askChatGpt(chatRequest, authorizationHeader);
        return ResponseEntity.ok(response);
    }
}
