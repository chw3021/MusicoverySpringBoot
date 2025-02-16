package com.musicovery.spotifyapi.service;

public interface SpotifyApiPlaylistService {

	String createPlaylist(String userId, String playlistName, String description);

	String updatePlaylist(String playlistId, String newName, String newDescription);

	String deletePlaylist(String playlistId);

	String addTrackToPlaylist(String playlistId, String trackUri);

	String removeTrackFromPlaylist(String playlistId, String trackUri);
}
