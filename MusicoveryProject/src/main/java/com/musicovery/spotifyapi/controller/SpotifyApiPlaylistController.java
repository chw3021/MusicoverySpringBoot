package com.musicovery.spotifyapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.spotifyapi.service.SpotifyApiPlaylistService;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyApiPlaylistController {

    private final SpotifyApiPlaylistService spotifyApiPlaylistService;

    public SpotifyApiPlaylistController(SpotifyApiPlaylistService spotifyApiPlaylistService) {
        this.spotifyApiPlaylistService = spotifyApiPlaylistService;
    }

    /**
     * 📂 플레이리스트 생성 API
     */
    @PostMapping("/playlist")
    public ResponseEntity<String> createPlaylist(@CookieValue(value = "MUSICOVERY_ACCESS_TOKEN", required = false) String accessToken, @RequestParam String name,
                                                 @RequestParam String description) {
        String result = spotifyApiPlaylistService.createPlaylist(accessToken, name, description);
        return ResponseEntity.ok(result);
    }

    /**
     * 📝 플레이리스트 수정 API
     */
    @PutMapping("/playlist/{playlistId}")
    public ResponseEntity<String> updatePlaylist(@CookieValue(value = "MUSICOVERY_ACCESS_TOKEN", required = false) String accessToken, @PathVariable String playlistId,
                                                 @RequestParam String name, @RequestParam String description) {
        String result = spotifyApiPlaylistService.updatePlaylist(accessToken, playlistId, name, description);
        return ResponseEntity.ok(result);
    }

    /**
     * ❌ 플레이리스트에서 노래 삭제 API (Track URI 기반)
     */
    @DeleteMapping("/playlist/{playlistId}/track")
    public ResponseEntity<String> removeTrackFromPlaylist(@CookieValue(value = "MUSICOVERY_ACCESS_TOKEN", required = false) String accessToken, @PathVariable String playlistId,
                                                          @RequestParam String trackUri) {
        String result = spotifyApiPlaylistService.removeTrackFromPlaylist(accessToken, playlistId, trackUri);
        return ResponseEntity.ok(result);
    }

    /**
     * 🗑 플레이리스트 삭제 API
     */
    @DeleteMapping("/playlist/{playlistId}")
    public ResponseEntity<String> deletePlaylist(@CookieValue(value = "MUSICOVERY_ACCESS_TOKEN", required = false) String accessToken, @PathVariable String playlistId) {
        String result = spotifyApiPlaylistService.deletePlaylist(accessToken, playlistId);
        return ResponseEntity.ok(result);
    }

    /**
     * ➕ 플레이리스트에 노래 추가 API (Track ID 기반)
     */
    @PostMapping("/playlist/{playlistId}/track")
    public ResponseEntity<String> addTrackToPlaylist(@CookieValue(value = "MUSICOVERY_ACCESS_TOKEN", required = false) String accessToken, @PathVariable String playlistId,
                                                     @RequestParam String trackId) {
        String result = spotifyApiPlaylistService.addTrackToPlaylist(accessToken, playlistId, trackId);
        return ResponseEntity.ok(result);
    }
}
