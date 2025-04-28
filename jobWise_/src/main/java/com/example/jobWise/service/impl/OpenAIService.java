package com.example.jobWise.service.impl;

import com.example.jobWise.dto.request.OpenAiRequest;
import com.example.jobWise.dto.response.OpenAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Component
public class OpenAIService {
    @Value("${openai.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .build();

    public String chat(String prompt, String model) {
        return webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(buildRequestBody(prompt, model))
                .retrieve()
                .bodyToMono(OpenAiResponse.class)
                .map(response -> response.getChoices().get(0).getMessage().getContent())
                .block();
    }

    private OpenAiRequest buildRequestBody(String prompt, String model) {
        return OpenAiRequest.builder()
                .model(model)
                .messages(Collections.singletonList(
                        OpenAiRequest.Message.builder()
                                .role("user")
                                .content(prompt)
                                .build()
                ))
                .build();
    }
}
