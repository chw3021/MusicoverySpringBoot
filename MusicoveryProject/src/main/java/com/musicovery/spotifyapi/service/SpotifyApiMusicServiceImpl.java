package com.musicovery.spotifyapi.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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


    /**
     * 🔍 음악 검색 (Spotify API 호출)
     */
    public String searchMusic(String keyword, String type) {
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

    /**
     * 🎵 음악 재생
     */
    @Override
    public void playMusic(String musicId) {
        String accessToken = spotifyAuthService.getAccessToken();
        String url = baseUrl + "/me/player/play";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("uris", List.of("spotify:track:" + musicId));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
    }

}
