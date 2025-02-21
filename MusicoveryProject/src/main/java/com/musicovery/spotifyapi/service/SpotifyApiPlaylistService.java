package com.musicovery.spotifyapi.service;

import java.util.List;

public interface SpotifyApiPlaylistService {


	String updatePlaylist(String accessToken, String playlistId, String name, String description);

	String removeTrackFromPlaylist(String accessToken, String playlistId, String trackUri);

	String deletePlaylist(String accessToken, String playlistId);

	String addTrackToPlaylist(String accessToken, String playlistId, String trackUri);
	
	List<String> getTracksInPlaylist(String playlistId);

	/**
	 * 📂 플레이리스트 생성
	 */
	String createPlaylist(String accessToken, String name, String description, List<String> tracks);
}
