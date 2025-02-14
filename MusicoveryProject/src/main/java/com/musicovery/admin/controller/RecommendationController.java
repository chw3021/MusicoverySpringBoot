package com.musicovery.admin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.admin.entity.Recommendation;
import com.musicovery.admin.service.RecommendationService;

@RestController
@RequestMapping("/admin/recommendations")
public class RecommendationController {
	private final RecommendationService recommendationService;

	public RecommendationController(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}

	// 추천 목록 조회
	@GetMapping
	public List<Recommendation> getAllRecommendations() {
		return recommendationService.getAllRecommendations();
	}

	// 추천 가중치 변경
	@PutMapping("/{recommendationId}/weight")
	public boolean updateRecommendationWeight(@PathVariable Long recommendationId, @RequestParam double weightAmount) {
		return recommendationService.updateRecommendationWeight(recommendationId, weightAmount);
	}
}
