package com.musicovery.spotifyapi.service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicovery.spotifyapi.common.SpotifyApiUtil;
import com.musicovery.spotifyapi.dto.SpotifyApiRequestDTO;

@Service
public class SpotifyApiMusicServiceImpl implements SpotifyApiMusicService {

    private final SpotifyApiUtil spotifyApiUtil;
    private final SpotifyAuthServiceImpl spotifyAuthService;
    private final RestTemplate restTemplate;

    @Value("${spotify.api.base_url}")
    private String baseUrl;

    public SpotifyApiMusicServiceImpl(SpotifyApiUtil spotifyApiUtil, SpotifyAuthServiceImpl spotifyAuthService) {
        this.spotifyApiUtil = spotifyApiUtil;
        this.spotifyAuthService = spotifyAuthService;
        this.restTemplate = new RestTemplate();
    }
    
    @Override
    public String searchTrack(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = String.format("%s/search?q=%s&type=track&limit=1", baseUrl, encodedQuery);
        
        SpotifyApiRequestDTO request = new SpotifyApiRequestDTO(url, "GET");
        String response = spotifyApiUtil.callSpotifyApi(request, null);
        
        
        return response;
    }

    @Override
    public String searchArtist(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = String.format("%s/search?q=%s&type=artist&market=KR&limit=1", baseUrl, encodedQuery);

        SpotifyApiRequestDTO request = new SpotifyApiRequestDTO(url, "GET");
        String response = spotifyApiUtil.callSpotifyApi(request, null);

        // 응답에서 아티스트 ID 추출
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> result = objectMapper.readValue(response, Map.class);
            List<Map<String, Object>> artists = (List<Map<String, Object>>) ((Map<String, Object>) result.get("artists")).get("items");
            
            if (artists != null && !artists.isEmpty()) {
                return (String) artists.get(0).get("id"); // 첫 번째 아티스트 ID 반환
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null; // 아티스트를 찾을 수 없으면 null 반환
    }

    public ResponseEntity<Map<String, Object>> getTracksByArtist(String artistId) {
        try {
            String accessToken = spotifyAuthService.getAccessToken();
            String url = "https://api.spotify.com/v1/artists/" + artistId + "/top-tracks?market=KR";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // JSON String → Map 변환
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);

            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            // 보다 자세한 에러 메시지 로깅
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "서버 오류 발생", "message", e.getMessage()));
        }
    }
    
//    public String getTracksByArtist(String artistId) {
//        String accessToken = spotifyAuthService.getAccessToken();
//        String url = SPOTIFY_API_URL + artistId + "/top-tracks?market=KR"; // 한국 시장 기준
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + accessToken);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//
//        return response.getBody();
//    }
    

    /**
     * 🔍 음악 검색 (Spotify API 호출)
     */
    public String search(String keyword, String type) {
		String accessToken = spotifyAuthService.getAccessToken();
        String url = baseUrl + "/search?q=" + keyword + "&type=" + type;

		SpotifyApiRequestDTO request = new SpotifyApiRequestDTO(url, "GET");

		return spotifyApiUtil.callSpotifyApi(request, accessToken);
    }

    public String getTracksByIds(List<String> trackIds) {
		String accessToken = spotifyAuthService.getAccessToken();
        String ids = String.join(",", trackIds);
        String url = baseUrl + "/tracks?ids=" + ids;
        SpotifyApiRequestDTO request = new SpotifyApiRequestDTO(url, "GET");
		return spotifyApiUtil.callSpotifyApi(request, accessToken);
    }

    /**
     * 🎵 음악 추천
     *
     * @param seedArtists  콤마로 구분된 시드 아티스트 ID 목록 (예: "artist_id1,artist_id2")
     * @param seedTracks   콤마로 구분된 시드 트랙 ID 목록 (예: "track_id1,track_id2")
     * @param seedGenres   콤마로 구분된 시드 장르 목록 (예: "pop,rock")
     * @param minDanceability 최소 danceability 값 (0.0 - 1.0)
     * @param maxDanceability 최대 danceability 값 (0.0 - 1.0)
     * @param targetDanceability 목표 danceability 값 (0.0 - 1.0)
     * @param minEnergy    최소 energy 값 (0.0 - 1.0)
     * @param maxEnergy    최대 energy 값 (0.0 - 1.0)
     * @param targetEnergy 목표 energy 값 (0.0 - 1.0)
     * @param limit        추천 트랙 수 (기본값: 20, 최대값: 100)
     * @return 추천된 트랙 목록
     */
    public String getRecommendedTracks(
            String seedArtists,
            String seedTracks,
            String seedGenres,
            Double minDanceability,
            Double maxDanceability,
            Double targetDanceability,
            Double minEnergy,
            Double maxEnergy,
            Double targetEnergy,
            Double targetTempo,
            Integer limit) {

		String accessToken = spotifyAuthService.getAccessToken();
        StringBuilder urlBuilder = new StringBuilder(baseUrl + "/recommendations?");

        if (seedArtists != null && !seedArtists.isEmpty()) {
            urlBuilder.append("seed_artists=").append(seedArtists).append("&");
        }
        if (seedTracks != null && !seedTracks.isEmpty()) {
            urlBuilder.append("seed_tracks=").append(seedTracks).append("&");
        }
        if (seedGenres != null && !seedGenres.isEmpty()) {
            urlBuilder.append("seed_genres=").append(seedGenres).append("&");
        }
        if (minDanceability != null) {
            urlBuilder.append("min_danceability=").append(minDanceability).append("&");
        }
        if (maxDanceability != null) {
            urlBuilder.append("max_danceability=").append(maxDanceability).append("&");
        }
        if (targetDanceability != null) {
            urlBuilder.append("target_danceability=").append(targetDanceability).append("&");
        }
        if (minEnergy != null) {
            urlBuilder.append("min_energy=").append(minEnergy).append("&");
        }
        if (maxEnergy != null) {
            urlBuilder.append("max_energy=").append(maxEnergy).append("&");
        }
        if (targetEnergy != null) {
            urlBuilder.append("target_energy=").append(targetEnergy).append("&");
        }
        if (targetTempo != null) {
            urlBuilder.append("target_tempo=").append(targetTempo).append("&");
        }
        if (limit != null) {
            urlBuilder.append("limit=").append(limit).append("&");
        }

        // 마지막 '&' 제거
        String url = urlBuilder.substring(0, urlBuilder.length() - 1);

        SpotifyApiRequestDTO request = new SpotifyApiRequestDTO(url, "GET");
		return spotifyApiUtil.callSpotifyApi(request, accessToken);
    }
    
    @Override
    public String playMusic(String accessToken, String musicId, String deviceId) {
        String url = baseUrl + "/me/player/play";
        if (deviceId != null && !deviceId.isEmpty()) {
            url += "?device_id=" + deviceId;
        }

        // 🎵 API 요청 본문 생성
        Map<String, Object> body = new HashMap<>();
        body.put("uris", List.of("spotify:track:" + musicId));
        String requestBody;
        
        try {
            requestBody = new ObjectMapper().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 중 오류 발생", e);
        }

        // 🎵 Spotify API 호출
        return spotifyApiUtil.callSpotifyApi(accessToken, new SpotifyApiRequestDTO(url, "PUT"), requestBody);
    }

    @Override
    public String getDevices(String accessToken) {
        String url = baseUrl + "/me/player/devices";
        return spotifyApiUtil.callSpotifyApi(accessToken, new SpotifyApiRequestDTO(url, "GET"), null);
    }
}
