package com.musicovery.spotifyapi.service;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
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
	private Instant tokenExpiryTime;


    @PostConstruct
    public void init() {
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(URI.create(redirectUri))
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
	
	private final Map<String, TokenInfo> userTokens = new ConcurrentHashMap<>(); // 사용자별 토큰 저장

    @Override
    public String requestUserAccessToken(String code) {
        try {
            AuthorizationCodeRequest authRequest = spotifyApi.authorizationCode(code).build();
            
            
            AuthorizationCodeCredentials credentials = authRequest.execute();
//            userTokens.put(sessionId, new TokenInfo(credentials.getAccessToken(), credentials.getRefreshToken(),
//                    Instant.now().plusSeconds(credentials.getExpiresIn())));

            return credentials.getAccessToken();
        } catch (Exception e) {
            System.err.println("Spotify 인증 실패: {}"+ e.getMessage());
            e.printStackTrace();
            throw new IllegalStateException("Failed to request access token", e);
        }
    }

    @Override
    public String refreshUserAccessToken(String sessionId) {
        TokenInfo tokenInfo = userTokens.get(sessionId);
        if (tokenInfo == null || tokenInfo.refreshToken == null) {
            throw new IllegalStateException("No refresh token available for session: " + sessionId);
        }

        try {
            AuthorizationCodeRefreshRequest refreshRequest = spotifyApi.authorizationCodeRefresh()
                    .refresh_token(tokenInfo.refreshToken).build();

            AuthorizationCodeCredentials credentials = refreshRequest.execute();

            userTokens.put(sessionId, new TokenInfo(credentials.getAccessToken(), tokenInfo.refreshToken,
                    Instant.now().plusSeconds(credentials.getExpiresIn())));

            return credentials.getAccessToken();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to refresh access token", e);
        }
    }

    @Override
    public String getValidUserAccessToken(String sessionId) {
        TokenInfo tokenInfo = userTokens.get(sessionId);

        if (tokenInfo == null || Instant.now().isAfter(tokenInfo.expiryTime)) {
            return refreshUserAccessToken(sessionId);
        }
        return tokenInfo.accessToken;
    }

    @Override
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