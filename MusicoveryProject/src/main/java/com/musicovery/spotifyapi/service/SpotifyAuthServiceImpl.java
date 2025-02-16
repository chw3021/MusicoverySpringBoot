package com.musicovery.spotifyapi.service;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
@Service
public class SpotifyAuthServiceImpl implements SpotifyAuthService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.api.token_url}")
    private String tokenUrl;

    @Value("${spotify.api.redirect_uri}")
    private String redirectUri;

    private SpotifyApi spotifyApi;
    private String accessToken;
	private String userAccessToken;
	private String refreshToken;
	private Instant tokenExpiryTime;

	private final Map<String, TokenInfo> userTokens = new ConcurrentHashMap<>(); // 사용자별 토큰 저장

    private final RestTemplate restTemplate;

    public SpotifyAuthServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() {
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
		requestAccessToken();
    }

	// accessToken 관련 메서드
	private void requestAccessToken() {
		try {
			ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
			ClientCredentials clientCredentials = clientCredentialsRequest.execute();
			this.accessToken = clientCredentials.getAccessToken();
			this.tokenExpiryTime = Instant.now().plusSeconds(clientCredentials.getExpiresIn());
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			throw new IllegalStateException("Failed to retrieve access token", e);
        }
    }

	public String getAccessToken() {
		if (Instant.now().isAfter(tokenExpiryTime)) {
			requestAccessToken();
		}
		return accessToken;
    }

	// ======= userAccessToken 관련 메서드 =======
	/**
	 * 사용자의 액세스 토큰 요청 후 저장
	 */
	public String requestUserAccessToken(String code) {
		try {
			AuthorizationCodeRequest authRequest = spotifyApi.authorizationCode(code).build();
			AuthorizationCodeCredentials credentials = authRequest.execute();

			// 사용자 ID 가져오기 (Spotify API 요청)
			String userId = fetchSpotifyUserId(credentials.getAccessToken());

			// 사용자별 토큰 저장
			userTokens.put(userId, new TokenInfo(credentials.getAccessToken(), credentials.getRefreshToken(),
					Instant.now().plusSeconds(credentials.getExpiresIn())));

			return credentials.getAccessToken();
		} catch (Exception e) {
			throw new IllegalStateException("Failed to request access token", e);
		}
	}

	/**
	 * 사용자 ID 기반으로 토큰 리프레시
	 */
	public String refreshUserAccessToken(String userId) {
		TokenInfo tokenInfo = userTokens.get(userId);
		if (tokenInfo == null || tokenInfo.refreshToken == null) {
			throw new IllegalStateException("No refresh token available for user: " + userId);
		}

		try {
			AuthorizationCodeRefreshRequest refreshRequest = spotifyApi.authorizationCodeRefresh()
					.refresh_token(tokenInfo.refreshToken).build();

			AuthorizationCodeCredentials credentials = refreshRequest.execute();

			// 새 토큰 업데이트
			userTokens.put(userId, new TokenInfo(credentials.getAccessToken(), tokenInfo.refreshToken,
					Instant.now().plusSeconds(credentials.getExpiresIn())));

			return credentials.getAccessToken();
		} catch (Exception e) {
			throw new IllegalStateException("Failed to refresh access token", e);
		}
	}

	public String getValidUserAccessToken(String userId) {
		TokenInfo tokenInfo = userTokens.get(userId);

		if (tokenInfo == null || Instant.now().isAfter(tokenInfo.expiryTime)) {
			return refreshUserAccessToken(userId);
		}
		return tokenInfo.accessToken;
	}

	/**
	 * Spotify API를 호출하여 사용자 ID 가져오기
	 */
	public String fetchSpotifyUserId(String accessToken) {
		try {
			SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();

			GetCurrentUsersProfileRequest request = spotifyApi.getCurrentUsersProfile().build();
			User user = request.execute();

			return user.getId(); // Spotify User ID 반환
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			throw new IllegalStateException("Failed to fetch Spotify User ID", e);
		}
	}
	public String getSpotifyAuthUrl() {
	    String scope = String.join(" ",
	        "user-read-private",
	        "user-read-email",
	        "playlist-modify-public",
	        "playlist-modify-private",
	        "user-read-recently-played",
	        "user-top-read",
	        "user-read-playback-state",
	        "user-modify-playback-state",
	        "user-read-currently-playing",
	        "streaming",
	        "user-follow-modify",
	        "user-follow-read",
	        "user-library-read",
	        "user-library-modify"
	    );

	    return "https://accounts.spotify.com/authorize" +
	            "?response_type=code" +
	            "&client_id=" + clientId +
	            "&redirect_uri=" + redirectUri +
	            "&scope=" + scope;
	}

	/**
	 * 사용자 토큰을 저장하는 내부 클래스
	 */
	private static class TokenInfo {
		String accessToken;
		String refreshToken;
		Instant expiryTime;

		TokenInfo(String accessToken, String refreshToken, Instant expiryTime) {
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
			this.expiryTime = expiryTime;
		}
	}

}