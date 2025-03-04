package com.musicovery.music.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.music.entity.Music;
import com.musicovery.music.service.MusicService;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    // 음악 ID로 음악 정보 가져오기
    @GetMapping("/{musicId}")
    public Music getMusicById(@PathVariable String musicId) {
        return musicService.getMusicById(musicId);
    }

    // 음악 데이터 저장하기
    @PostMapping("/save")
    public Music addMusic(@RequestBody Music music) {
        return musicService.saveMusic(music);
    }

    /**
     * 🎵 음악 재생
     */
    @PutMapping("/play")
    public ResponseEntity<String> playMusic(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam String musicId,
            @RequestParam(required = false) String deviceId) {
        String accessToken = bearerToken.replace("Bearer ", "");
        musicService.playMusic(accessToken, musicId, deviceId);
        return ResponseEntity.ok("음악 재생 요청 완료!");
    }

    /**
     * 🎵 디바이스 목록 가져오기
     */
    @GetMapping("/devices")
    public ResponseEntity<String> getDevices(
            @RequestHeader("Authorization") String bearerToken) {
        String accessToken = bearerToken.replace("Bearer ", "");
        String devices = musicService.getDevices(accessToken);
        return ResponseEntity.ok(devices);
    }
}