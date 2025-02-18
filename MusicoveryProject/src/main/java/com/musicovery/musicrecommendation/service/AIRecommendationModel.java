package com.musicovery.musicrecommendation.service;

import java.util.List;

import com.musicovery.musicrecommendation.entity.Recommendation;

public interface AIRecommendationModel {
    List<Recommendation> getRecommendedTracks(String userId);

	String trainModel();
}
