package com.musicovery.spotifyapi.service;

import se.michaelthelin.spotify.SpotifyApi;

public interface SpotifyAuthService {
	String getAccessToken();


	/**
	 * 사용자의 액세스 토큰 요청 후 저장
	 */
	String requestUserAccessToken(String code);

	String refreshUserAccessToken(String refreshToken);


	String getSpotifyAuthUrl();


	SpotifyApi getSpotifyApi();


}
