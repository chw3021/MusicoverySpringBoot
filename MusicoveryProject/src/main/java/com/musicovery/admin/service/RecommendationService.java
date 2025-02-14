package com.musicovery.admin.service;

import java.util.List;

import com.musicovery.admin.entity.Recommendation;

public interface RecommendationService {
	List<Recommendation> getAllRecommendations();

	boolean updateRecommendationWeight(Long recommendationId, double weightAmount);
}
