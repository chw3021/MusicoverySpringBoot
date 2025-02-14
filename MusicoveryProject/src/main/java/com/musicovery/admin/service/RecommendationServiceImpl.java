package com.musicovery.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicovery.admin.entity.Recommendation;
import com.musicovery.admin.repository.RecommendationRepository;

@Service
public class RecommendationServiceImpl implements RecommendationService {
	private final RecommendationRepository recommendationRepository;

	public RecommendationServiceImpl(RecommendationRepository recommendationRepository) {
		this.recommendationRepository = recommendationRepository;
	}

	@Override
	public List<Recommendation> getAllRecommendations() {
		return recommendationRepository.findAll();
	}

	@Override
	public boolean updateRecommendationWeight(Long recommendationId, double weightAmount) {
		Recommendation recommendation = recommendationRepository.findById(recommendationId)
				.orElseThrow(() -> new RuntimeException("추천 데이터를 찾을 수 없습니다: " + recommendationId));
		recommendation.setWeight(weightAmount);
		recommendationRepository.save(recommendation);
		return true;
	}
}
