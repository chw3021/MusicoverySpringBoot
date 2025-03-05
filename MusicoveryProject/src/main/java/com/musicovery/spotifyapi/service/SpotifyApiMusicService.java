package com.musicovery.spotifyapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface SpotifyApiMusicService {
    String search(String keyword, String type);
    public String getRecommendedTracks(
            String seedArtists,
            String seedTracks,
            String seedGenres,
            Double minDanceability,
            Double maxDanceability,
            Double targetDanceability,
            Double minEnergy,
            Double maxEnergy,
            Double targetEnergy,
            Double targetTempo,
            Integer limit);
    String getTracksByIds(List<String> recommendedTrackIds);  // AI 추천 결과를 바탕으로 곡 조회
	/**
	 * 🎵 음악 재생
	 * @return 
	 */
	String playMusic(String accessToken, String musicId, String deviceId);
	String searchTrack(String query);
	/**
	 * 🎵 음악 재생
	 */
	String getDevices(String accessToken);
	String searchArtist(String query);
	ResponseEntity<Map<String, Object>> getTracksByArtist(String artistId);
}
