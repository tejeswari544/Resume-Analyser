package com.codegnan.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AIService {

    private final WebClient webClient;

    // ✅ Correct URL for GEMINI 2.0 Flash (your API KEY supports ONLY this)
    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";

    private final String apiKey = "YOUR_API_KEY";

    public AIService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public String generateAnalysis(String text) {

        String prompt = """
                Analyze this resume and give:
                • Strengths
                • Missing skills
                • Grammar improvements
                • Suitable job roles

                Resume text:
                """ + text;

        String requestBody = """
                {
                    "contents": [
                        {
                            "parts": [
                                {"text": "%s"}
                            ]
                        }
                    ]
                }
                """.formatted(prompt);

        return webClient.post()
                .uri(GEMINI_API_URL + "?key=" + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
