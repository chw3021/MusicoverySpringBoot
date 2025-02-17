package com.musicovery.music.service;

import org.springframework.stereotype.Service;

import com.musicovery.music.entity.Music;
import com.musicovery.music.repository.MusicRepository;

@Service
public class MusicService {

    private final MusicRepository musicRepository;

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    // 음악 ID로 음악을 찾기
    public Music getMusicById(String musicId) {
        return musicRepository.findByMusicId(musicId).orElse(null);
    }

    // 새로운 음악 저장하기
    public Music saveMusic(Music music) {
        return musicRepository.save(music);
    }
}