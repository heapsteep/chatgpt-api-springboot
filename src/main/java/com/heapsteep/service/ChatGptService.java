package com.heapsteep.service;

import com.heapsteep.model.ChatRequest;
import com.heapsteep.model.OpenAiRequest;
import com.heapsteep.model.OpenAiResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ChatGptService {


    //Using RestTemplate:
    private final RestTemplate restTemplate = new RestTemplate();

    public OpenAiResponse askChatGpt(ChatRequest chatRequest, String authHeader) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);

        OpenAiRequest.Message message = new OpenAiRequest.Message("user", chatRequest.getPrompt());
        OpenAiRequest requestBody = new OpenAiRequest("gpt-4.1", Collections.singletonList(message));

        HttpEntity<OpenAiRequest> httpEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<OpenAiResponse> response = restTemplate.postForEntity(url, httpEntity, OpenAiResponse.class);
        return response.getBody();
    }

    //Using WebClient:
    /*private final WebClient webClient;

    public ChatGptService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
    }

    public OpenAiResponse askChatGpt(ChatRequest chatRequest, String authHeader) {
        OpenAiRequest.Message message = new OpenAiRequest.Message("user", chatRequest.getPrompt());
        OpenAiRequest request = new OpenAiRequest("gpt-4.1", Collections.singletonList(message));

        return webClient.post()
                .uri("/chat/completions")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .body(Mono.just(request), OpenAiRequest.class)
                .retrieve()
                .bodyToMono(OpenAiResponse.class)
                .block(); // blocks to return the actual result
    }*/


}
