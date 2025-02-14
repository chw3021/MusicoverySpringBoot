package com.musicovery.musicrecommendation.service;

import java.util.List;

public interface AIRecommendationModel {
    List<String> getRecommendedTracks(String userId);
}
