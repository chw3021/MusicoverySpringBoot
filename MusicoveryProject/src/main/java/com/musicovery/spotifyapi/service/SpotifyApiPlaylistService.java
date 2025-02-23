package com.musicovery.spotifyapi.service;

import java.util.List;

public interface SpotifyApiPlaylistService {


	String updatePlaylist(String accessToken, String playlistId, String name, String description);

	String removeTrackFromPlaylist(String accessToken, String playlistId, String trackUri);

	String deletePlaylist(String accessToken, String playlistId);

	String addTrackToPlaylist(String accessToken, String playlistId, String trackUri);
	

	/**
	 * 📂 플레이리스트 생성
	 */
	String createPlaylist(String accessToken, String userId, String name, String description, List<String> tracks);

	/**
	 * 🎵 플레이리스트에 속한 모든 곡들의 ID를 가져오는 메서드
	 */
	String getTracksInPlaylist(String accessToken, String playlistId);

	/**
	 * 📝 플레이리스트 수정
	 */
	String updatePlaylistTracks(String accessToken, String playlistId, List<String> tracks);
}
