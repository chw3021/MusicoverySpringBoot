package com.musicovery.spotifyapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<String> getUserInfo(@CookieValue(value = "MUSICOVERY_ACCESS_TOKEN", required = false) String accessToken) {
        String userInfo = spotifyApiUserService.getUserInfo(accessToken);
        return ResponseEntity.ok(userInfo);
    }
}
