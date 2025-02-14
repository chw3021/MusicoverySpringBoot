package com.musicovery.spotifyapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.spotifyapi.service.SpotifyApiUserService;


@RestController
@RequestMapping("/api/spotify")
public class SpotifyApiUserController {

    private final SpotifyApiUserService spotifyApiUserService;

    public SpotifyApiUserController(SpotifyApiUserService spotifyApiUserService) {
        this.spotifyApiUserService = spotifyApiUserService;
    }

    @GetMapping("/userInfo")
    public ResponseEntity<String> getUserInfo(@RequestHeader("Authorization") String authorization) {
        // Authorization 헤더에서 "Bearer " 부분을 제거하고 액세스 토큰만 추출
        String accessToken = authorization.replace("Bearer ", "");

        // 액세스 토큰을 사용하여 Spotify API에서 사용자 정보 가져오기
        String userInfo = spotifyApiUserService.getUserInfo(accessToken);

        return ResponseEntity.ok(userInfo);
    }
}
