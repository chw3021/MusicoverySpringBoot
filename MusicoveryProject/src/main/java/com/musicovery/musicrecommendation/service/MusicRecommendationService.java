package com.musicovery.musicrecommendation.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicovery.gemini.dto.SongRecommendation;
import com.musicovery.gemini.service.GeminiService;
import com.musicovery.musicrecommendation.entity.Recommendation;
import com.musicovery.spotifyapi.service.SpotifyApiMusicService;

@Service
public class MusicRecommendationService {

    private final SpotifyApiMusicService spotifyApiMusicService;
    private final AIRecommendationModel aiModel;
    private final GeminiService geminiService;

    public MusicRecommendationService(SpotifyApiMusicService spotifyApiService, AIRecommendationModel aiModel, GeminiService geminiService) {
        this.spotifyApiMusicService = spotifyApiService;
        this.aiModel = aiModel;
		this.geminiService = geminiService;
    }

    /**
     * 🎵 AI 기반 추천
     */
    public String getAIRecommendedTracks(String userId) {
        // 1️⃣ AI 추천 모델을 사용하여 추천 목록 가져오기
        List<Recommendation> recommendedTracks = aiModel.getAiRecommendations(userId);
        
        // 2️⃣ 추천된 곡 ID를 바탕으로 Spotify에서 곡 정보를 가져오기
        List<String> trackIds = recommendedTracks.stream()
                .map(Recommendation::getMusicId)
                .collect(Collectors.toList());
        
        // 3️⃣ 추천된 곡 정보를 반환
        return spotifyApiMusicService.getTracksByIds(trackIds);
    }

    /**
     * 🔍 키워드 기반 추천 (장르, BPM, 분위기, 가사를 기반으로 추천)
     */
    public String getKeywordBasedRecommendations(String genres, String mood, Integer bpm) {
        // Gemini로부터 추천 목록 받기
        List<SongRecommendation> recommendations = geminiService.getRecommendations(genres, mood, bpm);
        
        // Spotify API를 통해 실제 트랙 정보 검색하고 결과를 String 리스트로 변환
        List<String> tracks = recommendations.stream()
            .map(rec -> {
                String query = rec.getTitle() + " " + rec.getArtist();
                return spotifyApiMusicService.searchTrack(query);
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        // ObjectMapper를 사용하여 JSON 문자열로 변환
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(tracks);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("추천 목록 변환 실패", e);
        }
    }
    
    public String getKeywordBasedRecommendations(String genre, Integer bpm, String mood, String lyrics) {
        String seedGenres = (genre != null && !genre.isEmpty()) ? genre : null;
        Double targetTempo = (bpm != null) ? bpm.doubleValue() : null;
        
        // 분위기(mood)에 따른 필터링 값 설정
        Double targetDanceability = null;
        Double targetEnergy = null;
        
        if (mood != null && !mood.isEmpty()) {
            switch (mood.toLowerCase()) {
                case "신나는":
                    targetDanceability = 0.8;
                    targetEnergy = 0.7;
                    break;
                case "잔잔한":
                    targetDanceability = 0.3;
                    targetEnergy = 0.4;
                    break;
                case "우울한":
                    targetDanceability = 0.2;
                    targetEnergy = 0.3;
                    break;
                case "행복한":
                    targetDanceability = 0.9;
                    targetEnergy = 0.8;
                    break;
            }
        }

        return spotifyApiMusicService.getRecommendedTracks(
                null, null, seedGenres, 
                null, null, targetDanceability, 
                null, null, targetEnergy, 
                targetTempo, 10
        );
    }
    
    /**
     * 🎲 깜짝 추천 (완전 랜덤 추천)
     */
    public String getSurpriseRecommendation() {
        return spotifyApiMusicService.getRecommendedTracks(null, null, null, null, null, null, null, null, null, null, 10);
    }
    
    /**
     * 🎼 가사 기반 추천 (사용자가 입력한 가사와 유사한 분위기의 노래 추천)
     */
    public String getLyricsBasedRecommendation(String lyricsSnippet) {
        return spotifyApiMusicService.searchMusic(lyricsSnippet, "track");
    }
}
