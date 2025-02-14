package com.musicovery.spotifyapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.spotifyapi.service.SpotifyApiService;

@RestController
@RequestMapping(value = "/spotifyapi")
public class SpotifyApiController {

    @Autowired
    private SpotifyApiService spotifyApiService;  // 인터페이스 사용

    @GetMapping("/search")
    public String searchTrack(@RequestParam String track) {
        return spotifyApiService.searchTrack(track);
    }
}
