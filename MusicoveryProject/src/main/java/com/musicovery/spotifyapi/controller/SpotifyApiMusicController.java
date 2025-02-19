package com.musicovery.spotifyapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.spotifyapi.service.SpotifyApiMusicService;
@RestController
@RequestMapping("/api/spotify")
public class SpotifyApiMusicController {

    private final SpotifyApiMusicService spotifyApiMusicService;

    public SpotifyApiMusicController(SpotifyApiMusicService spotifyApiService) {
        this.spotifyApiMusicService = spotifyApiService;
    }

    /**
     * 🔍 음악 검색 API
     */
    @GetMapping("/search")
    public ResponseEntity<String> searchMusic(@RequestParam String keyword, @RequestParam String type) {
   	
    	String result = spotifyApiMusicService.searchMusic(keyword, type);
        return ResponseEntity.ok(result);
    }

	/**
	 * 🎵 AI 추천 결과 기반으로 곡 정보 조회 API
	 */
	@GetMapping("/tracks")
	public ResponseEntity<String> getTracksByIds(@RequestParam List<String> trackIds) {
		String result = spotifyApiMusicService.getTracksByIds(trackIds);
		return ResponseEntity.ok(result);
	}
    /**
     * 🎵 음악 추천 API
     */
    @GetMapping("/recommendations")
    public ResponseEntity<String> getRecommendedTracks(
            @RequestParam(required = false) String seedArtists,
            @RequestParam(required = false) String seedTracks,
            @RequestParam(required = false) String seedGenres,
            @RequestParam(required = false) Double minDanceability,
            @RequestParam(required = false) Double maxDanceability,
            @RequestParam(required = false) Double targetDanceability,
            @RequestParam(required = false) Double minEnergy,
            @RequestParam(required = false) Double maxEnergy,
            @RequestParam(required = false) Double targetEnergy,
            @RequestParam(required = false) Double targetTempo,
            @RequestParam(required = false, defaultValue = "20") Integer limit) {

        String result = spotifyApiMusicService.getRecommendedTracks(
                seedArtists, seedTracks, seedGenres,
                minDanceability, maxDanceability, targetDanceability,
                minEnergy, maxEnergy, targetEnergy, targetTempo, limit);

        return ResponseEntity.ok(result);
    }
}
