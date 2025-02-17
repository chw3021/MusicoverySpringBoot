package com.musicovery.spotifyapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.spotifyapi.service.SpotifyApiUserService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/spotify")
public class SpotifyApiUserController {

    private final SpotifyApiUserService spotifyApiUserService;

    public SpotifyApiUserController(SpotifyApiUserService spotifyApiUserService) {
        this.spotifyApiUserService = spotifyApiUserService;
    }

    @GetMapping("/userInfo")
    public ResponseEntity<String> getUserInfo(HttpSession session) {
        String sessionId = session.getId(); // 세션 ID 가져오기
        String userInfo = spotifyApiUserService.getUserInfo(sessionId);
        return ResponseEntity.ok(userInfo);
    }
}
