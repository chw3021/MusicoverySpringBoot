package com.musicovery.spotifyapi.common;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.musicovery.spotifyapi.dto.SpotifyApiRequestDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpotifyApiUtil {
    
    public String callSpotifyApi(SpotifyApiRequestDTO api, String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken); // OAuth 2.0 인증 토큰 추가
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response;
        
        if ("GET".equalsIgnoreCase(api.getMethod())) {
            response = restTemplate.exchange(api.getUrl(), HttpMethod.GET, requestEntity, String.class);
        } else {
            throw new UnsupportedOperationException("현재 GET 방식만 지원됩니다.");
        }

        log.info("응답 코드: " + response.getStatusCode());
        return response.getBody();
    }
}
