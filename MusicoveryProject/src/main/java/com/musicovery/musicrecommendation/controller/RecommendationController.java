package com.musicovery.musicrecommendation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.musicrecommendation.service.RecommendationService;

@RestController
@RequestMapping("/admin/recommendations")
public class RecommendationController {
	private final RecommendationService recommendationService;

	public RecommendationController(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}

}
