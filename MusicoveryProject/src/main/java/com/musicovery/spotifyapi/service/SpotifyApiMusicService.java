package com.musicovery.spotifyapi.service;

import java.util.List;

public interface SpotifyApiMusicService {
    String searchMusic(String keyword, String type);
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
	String playMusic(String sessionId, String musicId);
}
