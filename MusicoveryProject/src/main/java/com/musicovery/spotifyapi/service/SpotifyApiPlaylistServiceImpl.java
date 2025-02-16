package com.musicovery.spotifyapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.musicovery.spotifyapi.common.SpotifyApiUtil;
import com.musicovery.spotifyapi.dto.SpotifyApiRequestDTO;

public class SpotifyApiPlaylistServiceImpl implements SpotifyApiPlaylistService {

	private final SpotifyApiUtil spotifyApiUtil;
	private final SpotifyAuthService spotifyAuthService;
	private final RestTemplate restTemplate;

	@Value("${spotify.api.base_url}")
	private String baseUrl;

	public SpotifyApiPlaylistServiceImpl(SpotifyApiUtil spotifyApiUtil, SpotifyAuthServiceImpl spotifyAuthService) {
		this.spotifyApiUtil = spotifyApiUtil;
		this.spotifyAuthService = spotifyAuthService;
		this.restTemplate = new RestTemplate();
	}

	public String createPlaylist(String userId, String name, String description) {
		String url = "https://api.spotify.com/v1/users/" + userId + "/playlists";
        String requestBody = "{ \"name\": \"" + name + "\", \"description\": \"" + description + "\", \"public\": true }";

		return spotifyApiUtil.callSpotifyApi(new SpotifyApiRequestDTO("POST", url), true, requestBody);
	}

	public String updatePlaylist(String playlistId, String name, String description) {
		String url = "https://api.spotify.com/v1/playlists/" + playlistId;
		String requestBody = "{ \"name\": \"" + name + "\", \"description\": \"" + description + "\" }";

		return spotifyApiUtil.callSpotifyApi(new SpotifyApiRequestDTO("PUT", url), true, requestBody);
	}

	public String removeTrackFromPlaylist(String playlistId, String trackUri) {
		String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        String requestBody = "{ \"tracks\": [{ \"uri\": \"" + trackUri + "\" }] }";

		return spotifyApiUtil.callSpotifyApi(new SpotifyApiRequestDTO("DELETE", url), true, requestBody);
	}

	@Override
	public String deletePlaylist(String playlistId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addTrackToPlaylist(String playlistId, String trackUri) {
		// TODO Auto-generated method stub
		return null;
	}
}
