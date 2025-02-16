package com.musicovery.spotifyapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.spotifyapi.common.SpotifyApiUtil;
import com.musicovery.spotifyapi.dto.SpotifyApiRequestDTO;
import com.musicovery.spotifyapi.service.SpotifyApiUserService;
import com.musicovery.spotifyapi.service.SpotifyAuthService;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyApiUserController {

    private final SpotifyApiUserService spotifyApiUserService;
	private final SpotifyAuthService spotifyAuthService;
	private final SpotifyApiUtil spotifyApiUtil;

	public SpotifyApiUserController(SpotifyApiUserService spotifyApiUserService, SpotifyAuthService spotifyAuthService,
			SpotifyApiUtil spotifyApiUtil) {
        this.spotifyApiUserService = spotifyApiUserService;
		this.spotifyAuthService = spotifyAuthService;
		this.spotifyApiUtil = spotifyApiUtil;
    }

    @GetMapping("/userInfo")
    public ResponseEntity<String> getUserInfo(@RequestHeader("Authorization") String authorization) {
		// Authorization 헤더에서 "Bearer " 부분을 제거하여 액세스 토큰 추출
        String accessToken = authorization.replace("Bearer ", "");

		// 사용자 ID 가져오기
		String userId = spotifyAuthService.fetchSpotifyUserId(accessToken);

		// Spotify API 호출을 위한 DTO 생성
		SpotifyApiRequestDTO requestDTO = new SpotifyApiRequestDTO("/v1/me", "GET");

		// `SpotifyApiUtil`을 사용하여 API 요청
		String userInfo = spotifyApiUtil.callSpotifyApi(userId, requestDTO, null);

        return ResponseEntity.ok(userInfo);
    }
}
