package com.musicovery.spotifyapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class SpotifyApiUserServiceImpl implements SpotifyApiUserService {

    private final RestTemplate restTemplate;  // HTTP 요청을 보내기 위한 RestTemplate

    @Value("${spotify.api.base_url}")
    private String baseUrl;  // Spotify API의 base URL

    public SpotifyApiUserServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String getUserInfo(String accessToken) {
        String url = baseUrl + "/me";  // 사용자 정보 가져오기 위한 URL

        // Authorization 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);  // accessToken을 Authorization 헤더에 추가
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        // Spotify API 호출 (GET /me)
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        return response.getBody();  // 사용자 정보 반환
    }
}
