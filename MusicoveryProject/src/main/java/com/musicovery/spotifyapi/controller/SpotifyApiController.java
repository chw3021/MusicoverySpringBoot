package com.musicovery.spotifyapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.spotifyapi.service.SpotifyApiMusicService;
@RestController
@RequestMapping("/api/spotify")
public class SpotifyApiController {

    private final SpotifyApiMusicService spotifyApiService;

    public SpotifyApiController(SpotifyApiMusicService spotifyApiService) {
        this.spotifyApiService = spotifyApiService;
    }

    /**
     * 🔍 음악 검색 API
     */
    @GetMapping("/search")
    public ResponseEntity<String> searchMusic(@RequestParam String keyword, @RequestParam String type) {
        System.out.println("dfa");
    	
    	String result = spotifyApiService.searchMusic(keyword, type);
        return ResponseEntity.ok(result);
    }

    /**
     * 📂 플레이리스트 생성 API
     */
    @PostMapping("/playlist")
    public ResponseEntity<String> createPlaylist(@RequestParam String userId,
                                                 @RequestParam String name,
                                                 @RequestParam String description) {
        String result = spotifyApiService.createPlaylist(userId, name, description);
        return ResponseEntity.ok(result);
    }

    /**
     * 📝 플레이리스트 수정 API
     */
    @PutMapping("/playlist/{playlistId}")
    public ResponseEntity<String> updatePlaylist(@PathVariable String playlistId,
                                                 @RequestParam String name,
                                                 @RequestParam String description) {
        String result = spotifyApiService.updatePlaylist(playlistId, name, description);
        return ResponseEntity.ok(result);
    }

    /**
     * ❌ 플레이리스트에서 노래 삭제 API
     */
    @DeleteMapping("/playlist/{playlistId}/track")
    public ResponseEntity<String> removeTrackFromPlaylist(@PathVariable String playlistId,
                                                          @RequestParam String trackUri) {
        String result = spotifyApiService.removeTrackFromPlaylist(playlistId, trackUri);
        return ResponseEntity.ok(result);
    }

    /**
     * 🎵 AI 추천 결과 기반으로 곡 정보 조회 API
     */
    @GetMapping("/tracks")
    public ResponseEntity<String> getTracksByIds(@RequestParam List<String> trackIds) {
        String result = spotifyApiService.getTracksByIds(trackIds);
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
            @RequestParam(required = false, defaultValue = "20") Integer limit) {

        String result = spotifyApiService.getRecommendedTracks(
                seedArtists, seedTracks, seedGenres,
                minDanceability, maxDanceability, targetDanceability,
                minEnergy, maxEnergy, targetEnergy, limit);

        return ResponseEntity.ok(result);
    }
}
