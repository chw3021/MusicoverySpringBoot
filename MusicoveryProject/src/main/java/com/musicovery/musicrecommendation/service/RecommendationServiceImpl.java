package com.musicovery.musicrecommendation.service;

import org.springframework.stereotype.Service;

import com.musicovery.musicrecommendation.repository.RecommendationRepository;

@Service
public class RecommendationServiceImpl implements RecommendationService {
	private final RecommendationRepository recommendationRepository;

	public RecommendationServiceImpl(RecommendationRepository recommendationRepository) {
		this.recommendationRepository = recommendationRepository;
	}
}

