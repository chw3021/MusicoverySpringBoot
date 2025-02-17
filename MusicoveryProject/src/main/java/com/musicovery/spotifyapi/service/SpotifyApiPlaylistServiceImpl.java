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

    /**
     * 📂 플레이리스트 생성
     */
    @Override
    public String createPlaylist(String sessionId, String name, String description) {
        String url = "https://api.spotify.com/v1/me/playlists";
        String requestBody = "{ \"name\": \"" + name + "\", \"description\": \"" + description + "\", \"public\": true }";

        return spotifyApiUtil.callSpotifyApi(sessionId, new SpotifyApiRequestDTO("POST", url), requestBody);
    }

    /**
     * 📝 플레이리스트 수정
     */
    @Override
    public String updatePlaylist(String sessionId, String playlistId, String name, String description) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId;
        String requestBody = "{ \"name\": \"" + name + "\", \"description\": \"" + description + "\" }";

        return spotifyApiUtil.callSpotifyApi(sessionId, new SpotifyApiRequestDTO("PUT", url), requestBody);
    }

    /**
     * ❌ 플레이리스트에서 노래 삭제 (Track URI 기반)
     */
    @Override
    public String removeTrackFromPlaylist(String sessionId, String playlistId, String trackUri) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        String requestBody = "{ \"tracks\": [{ \"uri\": \"" + trackUri + "\" }] }";

        return spotifyApiUtil.callSpotifyApi(sessionId, new SpotifyApiRequestDTO("DELETE", url), requestBody);
    }

    /**
     * 🗑 플레이리스트 삭제 (Spotify API에서는 "Unfollow" 방식)
     */
    @Override
    public String deletePlaylist(String sessionId, String playlistId) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/followers";

        return spotifyApiUtil.callSpotifyApi(sessionId, new SpotifyApiRequestDTO("DELETE", url), null);
    }

    /**
     * ➕ 플레이리스트에 노래 추가 (Track ID 기반)
     */
    @Override
    public String addTrackToPlaylist(String sessionId, String playlistId, String trackId) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        String trackUri = "spotify:track:" + trackId; // Track ID를 URI로 변환
        String requestBody = "{ \"uris\": [\"" + trackUri + "\"] }";

        return spotifyApiUtil.callSpotifyApi(sessionId, new SpotifyApiRequestDTO("POST", url), requestBody);
    }
}

