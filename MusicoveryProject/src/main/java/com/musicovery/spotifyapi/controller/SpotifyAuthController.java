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

@RestController
@RequestMapping("/api/spotify")
public class SpotifyAuthController {

    private final SpotifyAuthService spotifyAuthService;

    public SpotifyAuthController(SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthService = spotifyAuthService;
    }

	@GetMapping("/getUserAccessToken")
	public ResponseEntity<String> getUserAccessToken() {
		try {
			String accessToken = spotifyAuthService.getValidUserAccessToken();
			return ResponseEntity.ok(accessToken);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving access token: " + e.getMessage());
		}
	}

	/**
	 * Spotify 로그인 페이지로 자동 리디렉트
	 */
	@GetMapping("/getUserAccessTokenFirst")
	public ResponseEntity<Void> login(HttpServletResponse response) throws IOException {
		String authUrl = spotifyAuthService.getSpotifyAuthUrl();
		response.sendRedirect(authUrl); // Spotify 인증 페이지로 바로 리디렉트
		return ResponseEntity.ok().build();
    }

	/**
	 * Spotify OAuth 인증 완료 후, 액세스 토큰을 저장
	 */
    @GetMapping("/callback")
	public ResponseEntity<String> handleSpotifyCallback(@RequestParam("code") String code) {
		try {
			String accessToken = spotifyAuthService.requestUserAccessToken(code);
			return ResponseEntity.ok("Spotify 인증 완료! 액세스 토큰 발급 완료: " + accessToken);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Spotify 인증 실패: " + e.getMessage());
		}
	}

	/**
	 * 저장된 userAccessToken이 만료되었을 경우 refresh token을 이용하여 새 토큰을 발급
	 */
	@GetMapping("/refresh-token")
	public ResponseEntity<String> refreshToken() {
		try {
			String newAccessToken = spotifyAuthService.refreshUserAccessToken();
			return ResponseEntity.ok("User access token refreshed: " + newAccessToken);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error refreshing user access token: " + e.getMessage());
		}
	}

	/**
	 * 현재 유효한 user access token을 반환
	 */
	@GetMapping("/valid-token")
	public ResponseEntity<String> getValidToken() {
		try {
			String validAccessToken = spotifyAuthService.getValidUserAccessToken();
			return ResponseEntity.ok("Valid user access token: " + validAccessToken);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving valid user access token: " + e.getMessage());
		}
    }
   
}
