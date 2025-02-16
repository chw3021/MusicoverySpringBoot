package com.musicovery.spotifyapi.common;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.musicovery.spotifyapi.dto.SpotifyApiRequestDTO;
import com.musicovery.spotifyapi.service.SpotifyAuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpotifyApiUtil {
    
	// 자동으로 헤더에 인증토큰을 추가해줌.
	private final RestTemplate restTemplate = new RestTemplate();
	private final SpotifyAuthService spotifyAuthService; // 액세스 토큰 관리 서비스

	public SpotifyApiUtil(SpotifyAuthService spotifyAuthService) {
		this.spotifyAuthService = spotifyAuthService;
	}

	// 자동으로 헤더에 인증 토큰을 추가
	public String callSpotifyApi(SpotifyApiRequestDTO api, String body) {
		String userAccessToken = spotifyAuthService.getAccessToken(); // 자동으로 유효한 토큰 가져오기

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + userAccessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<String> response;

		switch (api.getMethod().toUpperCase()) {
		case "GET":
			response = restTemplate.exchange(api.getUrl(), HttpMethod.GET, requestEntity, String.class);
			break;
		case "POST":
			response = restTemplate.exchange(api.getUrl(), HttpMethod.POST, requestEntity, String.class);
			break;
		case "PUT":
			response = restTemplate.exchange(api.getUrl(), HttpMethod.PUT, requestEntity, String.class);
			break;
		case "DELETE":
			response = restTemplate.exchange(api.getUrl(), HttpMethod.DELETE, requestEntity, String.class);
			break;
		default:
			throw new UnsupportedOperationException("지원하지 않는 HTTP 메서드: " + api.getMethod());
		}

		log.info("응답 코드: " + response.getStatusCode());
		return response.getBody();
	}

	public String callSpotifyApi(String userId, SpotifyApiRequestDTO api, String body) {
		String userAccessToken = spotifyAuthService.getValidUserAccessToken(userId); // 사용자 ID 기반 토큰 가져오기

        HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + userAccessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response;

        switch (api.getMethod().toUpperCase()) {
            case "GET":
                response = restTemplate.exchange(api.getUrl(), HttpMethod.GET, requestEntity, String.class);
                break;
            case "POST":
                response = restTemplate.exchange(api.getUrl(), HttpMethod.POST, requestEntity, String.class);
                break;
            case "PUT":
                response = restTemplate.exchange(api.getUrl(), HttpMethod.PUT, requestEntity, String.class);
                break;
            case "DELETE":
                response = restTemplate.exchange(api.getUrl(), HttpMethod.DELETE, requestEntity, String.class);
                break;
            default:
                throw new UnsupportedOperationException("지원하지 않는 HTTP 메서드: " + api.getMethod());
        }

        log.info("응답 코드: " + response.getStatusCode());
        return response.getBody();
    }
}
