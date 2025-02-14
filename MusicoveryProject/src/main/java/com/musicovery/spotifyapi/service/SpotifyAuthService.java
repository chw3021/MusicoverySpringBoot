package com.musicovery.spotifyapi.service;

public interface SpotifyAuthService {
    // 액세스 토큰을 가져오는 메서드
    String getAccessToken();
	String getSpotifyAuthUrl(String scope);
	String requestAccessTokenWithCode(String code);
}
