package com.musicovery.musicrecommendation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AIRecommendationModelImpl implements AIRecommendationModel {

    private final RestTemplate restTemplate;

    @Value("${ai.recommendation.api}")
    private String aiRecommendationApi; // Flask 서버 URL (예: http://localhost:5000/recommend/)

    public AIRecommendationModelImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> getRecommendedTracks(String userId) {
        String url = aiRecommendationApi + userId;
        AIRecommendationResponse response = restTemplate.getForObject(url, AIRecommendationResponse.class);

        if (response != null && response.getRecommendations() != null) {
            return response.getRecommendations().stream()
                    .map(AIRecommendationResponse.Recommendation::getSongId)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    // AI 추천 결과를 받을 DTO
    public static class AIRecommendationResponse {
        private String userId;
        private List<Recommendation> recommendations;

        public String getUserId() { return userId; }
        public List<Recommendation> getRecommendations() { return recommendations; }

        public static class Recommendation {
            private String song_id;
            private double score;

            public String getSongId() { return song_id; }
            public double getScore() { return score; }
        }
    }
}
