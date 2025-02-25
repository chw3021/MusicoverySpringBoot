package com.musicovery.musicrecommendation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.music.entity.KeywordRecommendationRequest;
import com.musicovery.musicrecommendation.service.MusicRecommendationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/recommendation")
@Slf4j
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
        String recommendations = recommendationService.getAIRecommendedTracks(userId);
        if (recommendations == null || recommendations.isEmpty() || recommendations.equals("[]")) {
            log.info("AI 추천 결과가 비어있습니다.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("AI 추천 실패"); // 204 No Content 응답
        }
        return ResponseEntity.ok(recommendations);
    }
    /**
     * 🔍 키워드 기반 추천
     */
    @PostMapping("/keyword")
    public ResponseEntity<String> getKeywordBasedRecommendations(@RequestBody KeywordRecommendationRequest request) {
        return ResponseEntity.ok(recommendationService.getKeywordBasedRecommendations(
                request.getGenre(), request.getMood(), request.getBpm()
        ));
    }


    /**
     * 🎲 깜짝 추천 API
     */

    @GetMapping("/surprise")
    public ResponseEntity<String> getSurpriseRecommendations() {
        try {
            String recommendations = recommendationService.getRandomRecommendations();
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            log.error("깜짝 추천 생성 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("추천 목록 생성 실패");
        }
    }
    
    
    /**
     * 🎼 가사 기반 추천 API
     */
    @GetMapping("/lyrics")
    public ResponseEntity<String> getLyricsRecommendation(@RequestParam String lyrics) {
        return ResponseEntity.ok(recommendationService.getLyricsBasedRecommendation(lyrics));
    }
}
