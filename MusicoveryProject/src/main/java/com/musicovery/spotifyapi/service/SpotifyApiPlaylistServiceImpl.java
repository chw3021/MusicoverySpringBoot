package com.musicovery.spotifyapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    

    /**
     * 🎵 플레이리스트에 속한 모든 곡들의 ID를 가져오는 메서드
     */
    @Override
    public List<String> getTracksInPlaylist(String playlistId) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        List<String> trackIds = new ArrayList<>();

        // 첫 번째 요청 보내기 (플레이리스트의 첫 페이지)
        String response = spotifyApiUtil.callSpotifyApi("", new SpotifyApiRequestDTO("GET", url), null);
        
        // 응답에서 트랙 ID를 추출
        trackIds.addAll(extractTrackIdsFromResponse(response));

        // 페이지네이션 처리: 여러 페이지가 있을 경우 계속해서 트랙을 가져옴
        String nextUrl = getNextPageUrl(response);
        while (nextUrl != null) {
            response = spotifyApiUtil.callSpotifyApi("", new SpotifyApiRequestDTO("GET", nextUrl), null);
            trackIds.addAll(extractTrackIdsFromResponse(response));
            nextUrl = getNextPageUrl(response); // 다음 페이지 URL 추출
        }

        return trackIds;
    }
    

    /**
     * API 응답에서 트랙 ID 목록 추출
     */
    private List<String> extractTrackIdsFromResponse(String response) {
        List<String> trackIds = new ArrayList<>();
        // JSON 파싱 로직을 통해 트랙 ID를 추출 (예: Jackson 라이브러리 사용)
        // response를 JSON 객체로 변환하고, 트랙 ID를 추출하여 trackIds에 추가
        JsonNode jsonNode = null;
		try {
			jsonNode = new ObjectMapper().readTree(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JsonNode items = jsonNode.get("items");

        for (JsonNode item : items) {
            JsonNode track = item.get("track");
            if (track != null) {
                String trackId = track.get("id").asText();
                trackIds.add(trackId);
            }
        }

        return trackIds;
    }

    /**
     * 응답에서 "next" 페이지 URL 추출
     */
    private String getNextPageUrl(String response) {
        JsonNode jsonNode = null;
		try {
			jsonNode = new ObjectMapper().readTree(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JsonNode nextNode = jsonNode.get("next");
        if (nextNode != null && !nextNode.isNull()) {
            return nextNode.asText();
        }
        return null; // 더 이상 페이지가 없다면 null 반환
    }

}

