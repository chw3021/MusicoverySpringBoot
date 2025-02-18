package com.musicovery.musicrecommendation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.musicovery.musicrecommendation.entity.Recommendation;

@Service
public class AIRecommendationModelImpl implements AIRecommendationModel {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${ai.recommendation.api}")
    private String aiRecommendationApi; // Flask 서버 URL (예: http://localhost:5000/)

    @Override
    public List<Recommendation> getRecommendedTracks(String userId) {
        String url = aiRecommendationApi + "/recommend/" + userId;
        AIRecommendationResponse response = restTemplate.getForObject(url, AIRecommendationResponse.class);

        if (response != null && response.getRecommendations() != null) {
            return response.getRecommendations();
        }
        return List.of();
    }

    @Override
    public String trainModel() {
        String url = aiRecommendationApi + "train";
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "추천 모델 학습 완료";
        } else {
            return "추천 모델 학습 실패: " + response.getBody();
        }
    }
    
    // AI 추천 결과를 받을 DTO
    public static class AIRecommendationResponse {
        private String userId;
        private List<Recommendation> recommendations;

        public String getUserId() { return userId; }
        public List<Recommendation> getRecommendations() { return recommendations; }
    }
}