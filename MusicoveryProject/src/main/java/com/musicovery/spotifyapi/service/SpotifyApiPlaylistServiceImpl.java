package com.musicovery.spotifyapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicovery.spotifyapi.common.SpotifyApiUtil;
import com.musicovery.spotifyapi.dto.SpotifyApiRequestDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpotifyApiPlaylistServiceImpl implements SpotifyApiPlaylistService {

    private final SpotifyApiUtil spotifyApiUtil;
    private final ObjectMapper objectMapper;

    public SpotifyApiPlaylistServiceImpl(SpotifyApiUtil spotifyApiUtil,ObjectMapper objectMapper) {
        this.spotifyApiUtil = spotifyApiUtil;
        this.objectMapper = objectMapper;
    }

    /**
     * 📂 플레이리스트 생성
     */
    @Override
    public String createPlaylist(String accessToken, String userId, String name, String description, List<String> tracks) {
        String url = "https://api.spotify.com/v1/users/" + userId + "/playlists";
        String requestBody = "{ \"name\": \"" + name + "\", \"description\": \"" + description + "\", \"public\": true }";

        JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(spotifyApiUtil.callSpotifyApi(accessToken, new SpotifyApiRequestDTO(url, "POST"), requestBody));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String playlistId = jsonNode.get("id").asText();

        // 트랙 추가
        addTracksToPlaylist(accessToken, playlistId, tracks);

        return playlistId;
    }
    private void addTracksToPlaylist(String accessToken, String playlistId, List<String> tracks) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        
        // 요청 본문 생성
        StringBuilder requestBodyBuilder = new StringBuilder();
        requestBodyBuilder.append("{ \"uris\": [");
        for (int i = 0; i < tracks.size(); i++) {
            requestBodyBuilder.append("\"").append(tracks.get(i)).append("\"");
            if (i < tracks.size() - 1) {
                requestBodyBuilder.append(", ");
            }
        }
        requestBodyBuilder.append("] }");
        
        String requestBody = requestBodyBuilder.toString();
        spotifyApiUtil.callSpotifyApi(accessToken, new SpotifyApiRequestDTO(url, "POST"), requestBody);
    }
    /**
     * 📝 플레이리스트 수정
     */
    @Override
    public String updatePlaylist(String accessToken, String playlistId, String name, String description) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId;
        String requestBody = "{ \"name\": \"" + name + "\", \"description\": \"" + description + "\" }";

        return spotifyApiUtil.callSpotifyApi(accessToken, new SpotifyApiRequestDTO(url, "PUT"), requestBody);
    }

	/**
	 * 📝 플레이리스트 트랙 수정
	 */
	@Override
	public String updatePlaylistTracks(String accessToken, String playlistId, List<String> tracks) {
		String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
		// 요청 본문 생성
		StringBuilder requestBodyBuilder = new StringBuilder();
		requestBodyBuilder.append("{ \"uris\": [");
		for (int i = 0; i < tracks.size(); i++) {
			requestBodyBuilder.append("\"").append(tracks.get(i)).append("\"");
			if (i < tracks.size() - 1) {
				requestBodyBuilder.append(", ");
			}
		}
		requestBodyBuilder.append("] }");

		String requestBody = requestBodyBuilder.toString();
		spotifyApiUtil.callSpotifyApi(accessToken, new SpotifyApiRequestDTO(url, "PUT"), requestBody);
		return playlistId;
	}

    /**
     * ❌ 플레이리스트에서 노래 삭제 (Track URI 기반)
     */
    @Override
    public String removeTrackFromPlaylist(String accessToken, String playlistId, String trackUri) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        String requestBody = "{ \"tracks\": [{ \"uri\": \"" + trackUri + "\" }] }";

        return spotifyApiUtil.callSpotifyApi(accessToken, new SpotifyApiRequestDTO(url, "DELETE"), requestBody);
    }

    /**
     * 🗑 플레이리스트 삭제 (Spotify API에서는 "Unfollow" 방식)
     */
    @Override
    public String deletePlaylist(String accessToken, String playlistId) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/followers";

        return spotifyApiUtil.callSpotifyApi(accessToken, new SpotifyApiRequestDTO(url, "DELETE"), null);
    }

    /**
     * ➕ 플레이리스트에 노래 추가 (Track ID 기반)
     */
    @Override
    public String addTrackToPlaylist(String accessToken, String playlistId, String trackId) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        String trackUri = "spotify:track:" + trackId; // Track ID를 URI로 변환
        String requestBody = "{ \"uris\": [\"" + trackUri + "\"] }";

        return spotifyApiUtil.callSpotifyApi(accessToken, new SpotifyApiRequestDTO(url, "POST"), requestBody);
    }
    

    /**
     * 🎵 플레이리스트에 속한 모든 곡들의 ID를 가져오는 메서드
     */
    @Override
    public String getTracksInPlaylist(String accessToken, String playlistId) {
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        StringBuilder fullResponse = new StringBuilder();

        // 첫 번째 요청 보내기 (첫 페이지)
        String response = spotifyApiUtil.callSpotifyApi(accessToken, new SpotifyApiRequestDTO(url, "GET"), null);
        fullResponse.append(response);

        // 페이지네이션 처리 (여러 페이지 데이터를 하나로 합침)
        String nextUrl = getNextPageUrl(response);
        while (nextUrl != null) {
            response = spotifyApiUtil.callSpotifyApi(accessToken, new SpotifyApiRequestDTO(nextUrl, "GET"), null);
            fullResponse.append(response);
            nextUrl = getNextPageUrl(response);
        }

        return fullResponse.toString(); // 전체 JSON 반환
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
			e.printStackTrace();
		}
        JsonNode nextNode = jsonNode.get("next");
        if (nextNode != null && !nextNode.isNull()) {
            return nextNode.asText();
        }
        return null; // 더 이상 페이지가 없다면 null 반환
    }

}

