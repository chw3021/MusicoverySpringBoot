package com.musicovery.music.service;

import org.springframework.stereotype.Service;

import com.musicovery.music.entity.Music;
import com.musicovery.music.repository.MusicRepository;
import com.musicovery.musicrecommendation.service.WeightService;
import com.musicovery.spotifyapi.service.SpotifyApiMusicService;

import jakarta.transaction.Transactional;

@Service
public class MusicService {

    private final WeightService weightService;
    private final SpotifyApiMusicService spotifyApiMusicService;
    private final MusicRepository musicRepository;

    public MusicService(WeightService weightService, SpotifyApiMusicService spotifyApiMusicService,
                        MusicRepository musicRepository) {
        this.weightService = weightService;
        this.spotifyApiMusicService = spotifyApiMusicService;
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

    @Transactional
    public void playMusic(String userId, String musicId) {
        // 음악 ID를 확인하고 없으면 Spotify에서 가져와 저장
        if (!musicRepository.findByMusicId(musicId).isPresent()) {
            Music music = new Music();
            music.setMusicId(musicId);
            musicRepository.save(music);
        }

        // 가중치 증가
        weightService.increaseWeightForPlayedSong(userId, musicId);

        // Spotify에서 음악 재생 API 호출
        spotifyApiMusicService.playMusic(musicId);
    }
}