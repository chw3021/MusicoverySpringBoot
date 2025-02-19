package com.musicovery.spotifyapi.controller;


import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.spotifyapi.service.SpotifyAuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

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
	public ResponseEntity<String> handleSpotifyCallback(@RequestParam("code") String code, HttpServletResponse response) {
	    try {
	        AuthorizationCodeRequest authRequest = spotifyAuthService.getSpotifyApi().authorizationCode(code).build();
	        AuthorizationCodeCredentials credentials = authRequest.execute();

	        String accessToken = credentials.getAccessToken();
	        String refreshToken = credentials.getRefreshToken();

	        // 🎵 accessToken을 쿠키에 저장
	        Cookie accessTokenCookie = new Cookie("MUSICOVERY_ACCESS_TOKEN", accessToken);
	        accessTokenCookie.setHttpOnly(true);
	        accessTokenCookie.setPath("/");
	        accessTokenCookie.setMaxAge(credentials.getExpiresIn()); // 토큰 만료 시간 설정
	        response.addCookie(accessTokenCookie);

	        // 🎵 refreshToken도 쿠키에 저장
	        Cookie refreshTokenCookie = new Cookie("MUSICOVERY_REFRESH_TOKEN", refreshToken);
	        refreshTokenCookie.setHttpOnly(true);
	        refreshTokenCookie.setPath("/");
	        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 30); // 30일 유지
	        response.addCookie(refreshTokenCookie);

	        return ResponseEntity.ok("Spotify 인증 완료!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Spotify 인증 실패: " + e.getMessage());
	    }
	}
	
	@GetMapping("/refresh-token")
	public ResponseEntity<String> refreshToken(
	        @CookieValue(value = "MUSICOVERY_REFRESH_TOKEN", required = false) String refreshToken,
	        HttpServletResponse response) {
	    try {
	        if (refreshToken == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("리프레시 토큰이 없습니다. 다시 로그인해주세요.");
	        }

	        String newAccessToken = spotifyAuthService.refreshUserAccessToken(refreshToken);

	        // 🎵 새 accessToken을 쿠키에 저장
	        Cookie accessTokenCookie = new Cookie("MUSICOVERY_ACCESS_TOKEN", newAccessToken);
	        accessTokenCookie.setHttpOnly(true);
	        accessTokenCookie.setPath("/");
	        accessTokenCookie.setMaxAge(60 * 60); // 1시간 유지
	        response.addCookie(accessTokenCookie);

	        return ResponseEntity.ok("새로운 accessToken 발급 완료!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("accessToken 갱신 실패: " + e.getMessage());
	    }
	}

}
