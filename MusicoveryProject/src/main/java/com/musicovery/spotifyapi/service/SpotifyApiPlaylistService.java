package com.musicovery.spotifyapi.service;

import java.util.List;

public interface SpotifyApiPlaylistService {

	String createPlaylist(String accessToken, String playlistName, String description);

	String updatePlaylist(String accessToken, String playlistId, String name, String description);

	String removeTrackFromPlaylist(String accessToken, String playlistId, String trackUri);

	String deletePlaylist(String accessToken, String playlistId);

	String addTrackToPlaylist(String accessToken, String playlistId, String trackUri);
	
	List<String> getTracksInPlaylist(String playlistId);
}
