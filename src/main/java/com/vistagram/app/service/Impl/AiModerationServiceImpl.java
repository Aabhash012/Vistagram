package com.vistagram.app.service.Impl;

import com.vistagram.app.service.Interface.AiModerationService;
import com.vistagram.app.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AiModerationServiceImpl implements AiModerationService {

    @Value("${ai.api.key}")
    private String apiKey;
    @Value("${ai.enabled}")
    private boolean aiEnabled;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .build();

    @Override
    public boolean isToxic(String comment) {

        if (!aiEnabled) {
            return fallbackCheck(comment);
        }
        try {
            String prompt = "Check if the following comment is toxic or abusive. " +
                    "Answer only YES or NO.\nComment: " + comment;

            Map<String, Object> request = Map.of(
                    "model", "gpt-4o-mini",
                    "messages", List.of(
                            Map.of("role", "user", "content", prompt)
                    )
            );
            Map response = webClient.post()
                    .uri("/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            String result = ((Map)((List)response.get("choices")).get(0))
                    .get("message")
                    .toString();
            return result.toLowerCase().contains("yes");
        } catch (Exception e) {
            System.out.println("AI failed, switching to fallback: " + e.getMessage());
            return fallbackCheck(comment);
        }
    }
    private boolean fallbackCheck(String comment) {

        String lowerComment = comment.toLowerCase();

        return Constants.ModerationConstants.TOXIC_WORDS.stream()
                .anyMatch(lowerComment::contains);
    }
}
