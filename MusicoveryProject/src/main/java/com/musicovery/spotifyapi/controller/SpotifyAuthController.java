package com.musicovery.spotifyapi.controller;


import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.spotifyapi.service.SpotifyAuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyAuthController {

    private final SpotifyAuthService spotifyAuthService;

    public SpotifyAuthController(SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthService = spotifyAuthService;
    }

	/**
	 * Spotify 로그인 페이지로 자동 리디렉트
	 */
	@GetMapping("/getUserAccessToken")
	public ResponseEntity<Void> login(HttpServletResponse response) throws IOException {
		String authUrl = spotifyAuthService.getSpotifyAuthUrl();
		response.sendRedirect(authUrl); // Spotify 인증 페이지로 바로 리디렉트
		return ResponseEntity.ok().build();
    }
	

    @GetMapping("/callback")
    public ResponseEntity<String> handleSpotifyCallback(@RequestParam("code") String code, HttpSession session) {
        try {
            String sessionId = session.getId();
            String accessToken = spotifyAuthService.requestUserAccessToken(code, sessionId);
            return ResponseEntity.ok("Spotify 인증 완료! 액세스 토큰 발급 완료: " + accessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Spotify 인증 실패: " + e.getMessage());
        }
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(HttpSession session) {
        try {
            String sessionId = session.getId();
            String newAccessToken = spotifyAuthService.refreshUserAccessToken(sessionId);
            return ResponseEntity.ok("User access token refreshed: " + newAccessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error refreshing user access token: " + e.getMessage());
        }
    }
}
