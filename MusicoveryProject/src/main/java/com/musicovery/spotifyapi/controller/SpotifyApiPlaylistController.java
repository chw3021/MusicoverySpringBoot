package com.musicovery.spotifyapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.musicovery.spotifyapi.service.SpotifyApiPlaylistService;

public class SpotifyApiPlaylistController {

	private final SpotifyApiPlaylistService spotifyApiPlaylistService;

	public SpotifyApiPlaylistController(SpotifyApiPlaylistService spotifyApiPlaylistService) {
		this.spotifyApiPlaylistService = spotifyApiPlaylistService;
    }
	/**
	 * 📂 플레이리스트 생성 API
	 */
	@PostMapping("/playlist")
	public ResponseEntity<String> createPlaylist(@RequestParam String userId, @RequestParam String name,
			@RequestParam String description) {
		String result = spotifyApiPlaylistService.createPlaylist(userId, name, description);
		return ResponseEntity.ok(result);
	}

	/**
	 * 📝 플레이리스트 수정 API
	 */
	@PutMapping("/playlist/{playlistId}")
	public ResponseEntity<String> updatePlaylist(@PathVariable String playlistId, @RequestParam String name,
			@RequestParam String description) {
		String result = spotifyApiPlaylistService.updatePlaylist(playlistId, name, description);
		return ResponseEntity.ok(result);
	}

	/**
	 * ❌ 플레이리스트에서 노래 삭제 API
	 */
	@DeleteMapping("/playlist/{playlistId}/track")
	public ResponseEntity<String> removeTrackFromPlaylist(@PathVariable String playlistId,
			@RequestParam String trackUri) {
		String result = spotifyApiPlaylistService.removeTrackFromPlaylist(playlistId, trackUri);
		return ResponseEntity.ok(result);
	}


}
