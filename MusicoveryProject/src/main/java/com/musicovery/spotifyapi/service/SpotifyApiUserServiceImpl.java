package com.musicovery.spotifyapi.service;

import org.springframework.stereotype.Service;

import com.musicovery.spotifyapi.common.SpotifyApiUtil;
import com.musicovery.spotifyapi.dto.SpotifyApiRequestDTO;

@Service
public class SpotifyApiUserServiceImpl implements SpotifyApiUserService {

    private final SpotifyAuthService spotifyAuthService;
    private final SpotifyApiUtil spotifyApiUtil;

    public SpotifyApiUserServiceImpl(SpotifyAuthService spotifyAuthService, SpotifyApiUtil spotifyApiUtil) {
        this.spotifyAuthService = spotifyAuthService;
        this.spotifyApiUtil = spotifyApiUtil;
    }

    @Override
    public String getUserInfo(String sessionId) {
        // Spotify API 호출을 위한 DTO 생성
        SpotifyApiRequestDTO requestDTO = new SpotifyApiRequestDTO("/v1/me", "GET");

        // `SpotifyApiUtil`을 사용하여 API 요청 수행
        return spotifyApiUtil.callSpotifyApi(sessionId, requestDTO, null);
    }
}