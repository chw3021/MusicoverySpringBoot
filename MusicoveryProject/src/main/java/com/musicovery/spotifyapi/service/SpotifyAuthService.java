package com.musicovery.spotifyapi.service;

public interface SpotifyAuthService {
	String getAccessToken();


	/**
	 * 사용자의 액세스 토큰 요청 후 저장
	 */
	String requestUserAccessToken(String code);

	String refreshUserAccessToken(String sessionId);


	String getValidUserAccessToken(String sessionId);


	String getSpotifyAuthUrl();
}
