package com.musicovery.musicrecommendation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.musicrecommendation.service.AIRecommendationModel;

@RestController
@RequestMapping("/admin/recommendation")
public class AIRecommendationController {

    private final AIRecommendationModel aiRecommendationModel;

    public AIRecommendationController(AIRecommendationModel aiRecommendationModel) {
        this.aiRecommendationModel = aiRecommendationModel;
    }

    @PostMapping("/train")
    public ResponseEntity<String> trainModel() {
        String result = aiRecommendationModel.trainModel();
        return ResponseEntity.ok(result);
    }

    
    
}
