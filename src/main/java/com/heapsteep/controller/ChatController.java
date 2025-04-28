package com.heapsteep.controller;

import com.heapsteep.model.ChatRequest;
import com.heapsteep.model.OpenAiResponse;
import com.heapsteep.service.ChatGptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatGptService chatGptService;

    public ChatController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    //Old Blocking way:
    @PostMapping("/blocking")
    public ResponseEntity<OpenAiResponse> chat(
            @RequestBody ChatRequest chatRequest,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        OpenAiResponse response = chatGptService.askChatGptBlocking(chatRequest, authorizationHeader);
        return ResponseEntity.ok(response);
    }

    //Non- blocking way:
    @PostMapping("/reactive")
    public Mono<ResponseEntity<OpenAiResponse>> chatReactive(
            @RequestBody ChatRequest chatRequest,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        return chatGptService.askChatGptReactive(chatRequest, authorizationHeader)
                .map(response -> ResponseEntity.ok(response));
    }
}
