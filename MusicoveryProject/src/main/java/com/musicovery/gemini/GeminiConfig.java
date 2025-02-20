package com.musicovery.gemini;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.musicovery.gemini.service.GeminiService;

@Configuration
public class GeminiConfig {
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Bean
    public GeminiService geminiService() {
        return new GeminiService(geminiApiKey);
    }
}