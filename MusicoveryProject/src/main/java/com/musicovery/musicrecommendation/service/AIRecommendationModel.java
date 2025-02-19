package com.musicovery.musicrecommendation.service;

import java.util.List;

import com.musicovery.musicrecommendation.entity.Recommendation;

public interface AIRecommendationModel {
    List<Recommendation> getAiRecommendations(String userId);

	String trainModel();
}
