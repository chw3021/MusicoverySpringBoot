package com.musicovery.spotifyapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.spotifyapi.service.SpotifyApiPlaylistService;

import jakarta.servlet.http.HttpSession;

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
    public ResponseEntity<String> createPlaylist(HttpSession session, @RequestParam String name,
                                                 @RequestParam String description) {
        String sessionId = session.getId();
        String result = spotifyApiPlaylistService.createPlaylist(sessionId, name, description);
        return ResponseEntity.ok(result);
    }

    /**
     * 📝 플레이리스트 수정 API
     */
    @PutMapping("/playlist/{playlistId}")
    public ResponseEntity<String> updatePlaylist(HttpSession session, @PathVariable String playlistId,
                                                 @RequestParam String name, @RequestParam String description) {
        String sessionId = session.getId();
        String result = spotifyApiPlaylistService.updatePlaylist(sessionId, playlistId, name, description);
        return ResponseEntity.ok(result);
    }

    /**
     * ❌ 플레이리스트에서 노래 삭제 API (Track URI 기반)
     */
    @DeleteMapping("/playlist/{playlistId}/track")
    public ResponseEntity<String> removeTrackFromPlaylist(HttpSession session, @PathVariable String playlistId,
                                                          @RequestParam String trackUri) {
        String sessionId = session.getId();
        String result = spotifyApiPlaylistService.removeTrackFromPlaylist(sessionId, playlistId, trackUri);
        return ResponseEntity.ok(result);
    }

    /**
     * 🗑 플레이리스트 삭제 API
     */
    @DeleteMapping("/playlist/{playlistId}")
    public ResponseEntity<String> deletePlaylist(HttpSession session, @PathVariable String playlistId) {
        String sessionId = session.getId();
        String result = spotifyApiPlaylistService.deletePlaylist(sessionId, playlistId);
        return ResponseEntity.ok(result);
    }

    /**
     * ➕ 플레이리스트에 노래 추가 API (Track ID 기반)
     */
    @PostMapping("/playlist/{playlistId}/track")
    public ResponseEntity<String> addTrackToPlaylist(HttpSession session, @PathVariable String playlistId,
                                                     @RequestParam String trackId) {
        String sessionId = session.getId();
        String result = spotifyApiPlaylistService.addTrackToPlaylist(sessionId, playlistId, trackId);
        return ResponseEntity.ok(result);
    }
}
