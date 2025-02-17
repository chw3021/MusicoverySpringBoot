package com.musicovery.music.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}