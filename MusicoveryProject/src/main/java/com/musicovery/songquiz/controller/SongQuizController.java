package com.musicovery.songquiz.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.gemini.service.GeminiService;
import com.musicovery.songquiz.dto.SongQuizDTO;
import com.musicovery.songquiz.service.SongQuizService;

@RestController
@RequestMapping("/api")
public class SongQuizController {

    private final SongQuizService songQuizService;
    private final GeminiService geminservice;

    @Autowired
    public SongQuizController(SongQuizService songQuizService,GeminiService geminservice) {
        this.songQuizService = songQuizService;
        this.geminservice = geminservice;
    }

    @GetMapping("/quizlist")
    public List<SongQuizDTO> searchSongs(@RequestParam String artist) {
        return songQuizService.searchSongsByArtist(artist);
    }


    // 가사를 가져오는 메서드
    @GetMapping("/lyrics")
    public ResponseEntity<Map<String, String>> getLyrics(
            @RequestParam String artist,
            @RequestParam String title) {

        String lyrics = geminservice.getLyrics(artist, title);

        Map<String, String> response = new HashMap<>();
        response.put("lyrics", lyrics);

        return ResponseEntity.ok(response);
    }
    
    // 유사제목 얻는 메서드
    @GetMapping("/sometitle")
    public ResponseEntity<Map<String, String>> getSomeTitle(
            @RequestParam String title) {

        // 대체 제목 요청
        String alternativeTitles = geminservice.getSomeTitle(title);

        Map<String, String> response = new HashMap<>();
        response.put("sometitle", alternativeTitles);

        return ResponseEntity.ok(response);
    }

    
}
