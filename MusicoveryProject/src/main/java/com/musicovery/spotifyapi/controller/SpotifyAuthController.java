package com.musicovery.spotifyapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.spotifyapi.service.SpotifyAuthService;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyAuthController {

    private final SpotifyAuthService spotifyAuthService;

    public SpotifyAuthController(SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthService = spotifyAuthService;
    }

    @GetMapping("/access-token")
    public ResponseEntity<String> getAccessToken() {
        String accessToken = spotifyAuthService.getAccessToken();
        return ResponseEntity.ok(accessToken);
    }
   
}
