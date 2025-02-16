package com.musicovery.spotifyapi.service;

public interface SpotifyAuthService {
	String getAccessToken();

	String getSpotifyAuthUrl();


	String requestUserAccessToken(String code);

	String refreshUserAccessToken(String userId);

	String getValidUserAccessToken(String userId);

	String fetchSpotifyUserId(String accessToken);
}
