package com.musicovery.spotifyapi.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.musicovery.spotifyapi.common.SpotifyApiUtil;
import com.musicovery.spotifyapi.dto.SpotifyApiRequestDTO;

@Service
public class SpotifyApiServiceImpl implements SpotifyApiService {

    private final SpotifyApiUtil spotifyApiUtil;
    private final SpotifyAuthService spotifyAuthService;

    @Value("${spotify.api.base_url}")
    private String baseUrl;

    public SpotifyApiServiceImpl(SpotifyApiUtil spotifyApiUtil, SpotifyAuthService spotifyAuthService) {
        this.spotifyApiUtil = spotifyApiUtil;
        this.spotifyAuthService = spotifyAuthService;
    }

    @Override
    public String searchTrack(String trackName) {
        String accessToken = spotifyAuthService.getAccessToken(); // 인증 토큰 가져오기

        String url = baseUrl + "/search?q=" + trackName + "&type=track";
        SpotifyApiRequestDTO request = new SpotifyApiRequestDTO(url, "GET");

        return spotifyApiUtil.callSpotifyApi(request, accessToken);
    }
}
