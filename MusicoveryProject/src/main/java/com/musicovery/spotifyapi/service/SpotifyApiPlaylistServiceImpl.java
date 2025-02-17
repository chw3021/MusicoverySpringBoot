package com.musicovery.spotifyapi.service;

import org.springframework.stereotype.Service;

import com.musicovery.spotifyapi.common.SpotifyApiUtil;
import com.musicovery.spotifyapi.dto.SpotifyApiRequestDTO;


@Service
public class SpotifyApiPlaylistServiceImpl implements SpotifyApiPlaylistService {

    private final SpotifyApiUtil spotifyApiUtil;

    public SpotifyApiPlaylistServiceImpl(SpotifyApiUtil spotifyApiUtil) {
        this.spotifyApiUtil = spotifyApiUtil;
    }

    @Override
    public String createPlaylist(String sessionId, String name, String description) {
        String url = "https://api.spotify.com/v1/me/playlists";
        String requestBody = "{ \"name\": \"" + name + "\", \"description\": \"" + description + "\", \"public\": true }";

        return spotifyApiUtil.callSpotifyApi(sessionId, new SpotifyApiRequestDTO("POST", url), requestBody);
    }

    @Override
    public String updatePlaylist(String sessionId, String playlistId, String name, String description) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId;
        String requestBody = "{ \"name\": \"" + name + "\", \"description\": \"" + description + "\" }";

        return spotifyApiUtil.callSpotifyApi(sessionId, new SpotifyApiRequestDTO("PUT", url), requestBody);
    }

    @Override
    public String removeTrackFromPlaylist(String sessionId, String playlistId, String trackUri) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        String requestBody = "{ \"tracks\": [{ \"uri\": \"" + trackUri + "\" }] }";

        return spotifyApiUtil.callSpotifyApi(sessionId, new SpotifyApiRequestDTO("DELETE", url), requestBody);
    }

    @Override
    public String deletePlaylist(String sessionId, String playlistId) {
        // TODO: 플레이리스트 삭제 로직 추가
        return null;
    }

    @Override
    public String addTrackToPlaylist(String sessionId, String playlistId, String trackUri) {
        // TODO: 플레이리스트에 트랙 추가 로직 추가
        return null;
    }
}

