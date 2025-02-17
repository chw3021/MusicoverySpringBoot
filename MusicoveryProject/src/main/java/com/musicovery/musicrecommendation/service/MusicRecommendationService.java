package com.musicovery.musicrecommendation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicovery.spotifyapi.service.SpotifyApiMusicService;

@Service
public class MusicRecommendationService {

    private final SpotifyApiMusicService spotifyApiService;
    private final AIRecommendationModel aiModel;

    public MusicRecommendationService(SpotifyApiMusicService spotifyApiService, AIRecommendationModel aiModel) {
        this.spotifyApiService = spotifyApiService;
        this.aiModel = aiModel;
    }

    /**
     * 🎵 AI 기반 추천
     */
    public String getAIRecommendedTracks(String userId) {
        // 1️⃣ AI 추천 모델을 사용하여 선호 장르 또는 곡 ID 목록 가져오기
        List<String> recommendedTrackIds = aiModel.getRecommendedTracks(userId);
        
        // 2️⃣ 추천된 곡 ID를 바탕으로 Spotify에서 곡 정보를 가져오기
        return spotifyApiService.getTracksByIds(recommendedTrackIds);
    }
}
