package com.musicovery.spotifyapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.spotifyapi.service.SpotifyAuthService;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyAuthController {

    private final SpotifyAuthService spotifyAuthService;

    public SpotifyAuthController(SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthService = spotifyAuthService;
    }


    // 사용자에게 로그인 URL을 제공하는 메서드
    @GetMapping("/login-url")
    public ResponseEntity<String> getSpotifyLoginUrl(@RequestParam("scope") String scope) {
        String loginUrl = spotifyAuthService.getSpotifyAuthUrl(scope);
        return ResponseEntity.ok(loginUrl);
    }
    
    @GetMapping("/access-token")
    public ResponseEntity<String> getAccessToken() {
        String accessToken = spotifyAuthService.getAccessToken();
        return ResponseEntity.ok(accessToken);
    }

    @GetMapping("/callback")
    public ResponseEntity<String> callback(@RequestParam("code") String code) {
        // 인증 코드로 액세스 토큰 요청
        String accessToken = spotifyAuthService.requestAccessTokenWithCode(code);

        // 액세스 토큰을 받아 Spotify API에 사용자 정보 요청
        // 이 액세스 토큰을 사용해 API를 호출하거나 세션에 저장할 수 있습니다.
        
        return ResponseEntity.ok("Access User token: " + accessToken);
    }
   
}
