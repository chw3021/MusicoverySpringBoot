package com.musicovery.musicrecommendation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.music.entity.KeywordRecommendationRequest;
import com.musicovery.musicrecommendation.service.MusicRecommendationService;

@RestController
@RequestMapping("/recommendation")
public class MusicRecommendationController {

    private final MusicRecommendationService recommendationService;

    public MusicRecommendationController(MusicRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * 🎵 AI 기반 추천 API
     */
    @GetMapping("/ai")
    public ResponseEntity<String> getAIRecommendation(@RequestParam String userId) {
        return ResponseEntity.ok(recommendationService.getAIRecommendedTracks(userId));
    }


    /**
     * 🔍 키워드 기반 추천
     */
    @PostMapping("/keyword")
    public ResponseEntity<String> getKeywordBasedRecommendations(@RequestBody KeywordRecommendationRequest request) {
        return ResponseEntity.ok(recommendationService.getKeywordBasedRecommendations(
                request.getGenre(), request.getBpm(), request.getMood(), request.getLyrics()
        ));
    }


    /**
     * 🎲 깜짝 추천 API
     */
    @GetMapping("/surprise")
    public ResponseEntity<String> getSurpriseRecommendation() {
        return ResponseEntity.ok(recommendationService.getSurpriseRecommendation());
    }

    /**
     * 🎼 가사 기반 추천 API
     */
    @GetMapping("/lyrics")
    public ResponseEntity<String> getLyricsRecommendation(@RequestParam String lyrics) {
        return ResponseEntity.ok(recommendationService.getLyricsBasedRecommendation(lyrics));
    }
}
