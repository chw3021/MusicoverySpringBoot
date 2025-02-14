package com.musicovery.spotifyapi.service;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.musicovery.spotifyapi.dto.SpotifyTokenResponse;

import jakarta.annotation.PostConstruct;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

@Service
public class SpotifyAuthServiceImpl implements SpotifyAuthService{

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
    private String userAccessToken; // 사용자의 액세스 토큰

    private final RestTemplate restTemplate;

    public SpotifyAuthServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Spotify 인증 URL 생성 메서드
    public String getSpotifyAuthUrl(String scope) {
        String url = "https://accounts.spotify.com/authorize" +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=" + scope;
        return url;
    }
    
    @PostConstruct
    public void init() {
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
        requestAccessToken();  // 초기화 후 바로 토큰 요청
    }

    public String getAccessToken() {
        if (accessToken == null) {
            requestAccessToken();
        }
        return accessToken;
    }

    public String getUserAccessToken() {
        return userAccessToken;
    }

    private void requestAccessToken() {
        if (spotifyApi == null) {
            throw new IllegalStateException("Spotify API is not initialized.");
        }

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            accessToken = spotifyApi.getAccessToken();

            // 디버깅 로그 추가
            System.out.println("=======================");
            System.out.println("=======================");
            System.out.println("=======================");
            System.out.println("Access Token: " + accessToken);
            System.out.println("=======================");
            System.out.println("=======================");
            System.out.println("=======================");
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    

    public String requestAccessTokenWithCode(String code) {
        // 인증 코드로 액세스 토큰을 요청하는 로직
        String url = tokenUrl + "?grant_type=authorization_code&code=" + code + "&redirect_uri=" + redirectUri + "&client_id=" + clientId + "&client_secret=" + clientSecret;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<SpotifyTokenResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, SpotifyTokenResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            userAccessToken = response.getBody().getAccessToken();  // 사용자 토큰 추출
            System.out.println("User Access Token: " + userAccessToken);
        } else {
            throw new IllegalStateException("Failed to retrieve User Access Token");
        }

        return userAccessToken;
    }
}
