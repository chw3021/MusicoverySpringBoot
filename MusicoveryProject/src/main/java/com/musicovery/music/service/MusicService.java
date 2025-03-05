package com.musicovery.music.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicovery.music.entity.Music;
import com.musicovery.music.repository.MusicRepository;
import com.musicovery.musicrecommendation.service.WeightService;
import com.musicovery.spotifyapi.service.SpotifyApiMusicService;
import com.musicovery.spotifyapi.service.SpotifyApiUserService;

import jakarta.transaction.Transactional;

@Service
public class MusicService {

    private final WeightService weightService;
    private final SpotifyApiMusicService spotifyApiMusicService;
    private final SpotifyApiUserService spotifyApiUserService;
    private final MusicRepository musicRepository;

    public MusicService(WeightService weightService, SpotifyApiMusicService spotifyApiMusicService,
    		SpotifyApiUserService spotifyApiUserService,
                        MusicRepository musicRepository) {
        this.weightService = weightService;
        this.spotifyApiMusicService = spotifyApiMusicService;
        this.spotifyApiUserService = spotifyApiUserService;
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
    public void playMusic(String accessToken, String musicId, String deviceId) {
        String userInfoJson = spotifyApiUserService.getUserInfo(accessToken);
        String userId = extractUserIdFromJson(userInfoJson); // JSON에서 userId 추출

        // 🎵 음악 ID를 확인하고 없으면 Spotify에서 가져와 저장
        if (!musicRepository.findByMusicId(musicId).isPresent()) {
            Music music = new Music();
            music.setMusicId(musicId);
            musicRepository.save(music);
        }

        // 🎵 가중치 증가 (유저가 들은 노래에 대한 가중치 증가)
        weightService.increaseWeightForPlayedSong(userId, musicId);

        // 🎵 Spotify에서 음악 재생 API 호출
        spotifyApiMusicService.playMusic(accessToken, musicId, deviceId);
    }

    public String getDevices(String accessToken) {
        return spotifyApiMusicService.getDevices(accessToken);
    }
    
	 // JSON에서 userId 추출하는 메서드 (Jackson 사용)
	 private String extractUserIdFromJson(String json) {
	     try {
	         ObjectMapper objectMapper = new ObjectMapper();
	         JsonNode root = objectMapper.readTree(json);
	         return root.get("id").asText();
	     } catch (Exception e) {
	         throw new RuntimeException("유저 ID를 가져오는 중 오류 발생", e);
	     }
	 }
}