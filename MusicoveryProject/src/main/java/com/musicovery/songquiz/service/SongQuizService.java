package com.musicovery.songquiz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicovery.songquiz.dto.SongQuizDTO;
import com.musicovery.spotifyapi.service.SpotifyApiMusicService;

@Service
public class SongQuizService {
    private final SpotifyApiMusicService spotifyApiService;

    @Autowired
    public SongQuizService(SpotifyApiMusicService spotifyApiService) {
        this.spotifyApiService = spotifyApiService;
    }

    public List<SongQuizDTO> searchSongsByArtist(String artistName) {
        String responseJson = spotifyApiService.search(artistName, "track");
        
        ObjectMapper objectMapper = new ObjectMapper();
        List<SongQuizDTO> songList = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(responseJson);
            JsonNode tracks = root.path("tracks").path("items");

            for (JsonNode track : tracks) {
                String title = track.path("name").asText();
                String artist = track.path("artists").get(0).path("name").asText();
                songList.add(new SongQuizDTO(artist, title, ""));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("노래 검색 실패", e);
        }

        return songList;
    }
}
