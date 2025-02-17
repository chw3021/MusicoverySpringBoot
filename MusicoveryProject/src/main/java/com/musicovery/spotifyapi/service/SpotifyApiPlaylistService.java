package com.musicovery.spotifyapi.service;

import java.util.List;

public interface SpotifyApiPlaylistService {

	String createPlaylist(String sessionId, String playlistName, String description);

	String updatePlaylist(String sessionId, String playlistId, String name, String description);

	String removeTrackFromPlaylist(String sessionId, String playlistId, String trackUri);

	String deletePlaylist(String sessionId, String playlistId);

	String addTrackToPlaylist(String sessionId, String playlistId, String trackUri);
	
	List<String> getTracksInPlaylist(String playlistId);
}
