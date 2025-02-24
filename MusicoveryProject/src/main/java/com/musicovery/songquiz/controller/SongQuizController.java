package com.musicovery.songquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.songquiz.dto.SongQuizDTO;
import com.musicovery.songquiz.entity.SongQuiz;
import com.musicovery.songquiz.service.SongQuizService;

@RestController
@RequestMapping("/api/songquiz")
public class SongQuizController {

    private final SongQuizService songQuizService;

    @Autowired
    public SongQuizController(SongQuizService songQuizService) {
        this.songQuizService = songQuizService;
    }

    @GetMapping("/lyrics")
    public SongQuizDTO getLyrics(@RequestParam String artist, @RequestParam String title) {
        SongQuiz song = SongQuiz.builder()
        		.artist(artist)
        		.title(title)
        		.build();
        return songQuizService.getLyrics(song);
    }
}