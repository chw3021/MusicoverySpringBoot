package com.musicovery.songquiz.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.songquiz.dto.SongQuizDTO;
import com.musicovery.songquiz.entity.SongQuiz;
import com.musicovery.songquiz.service.SongQuizService;

@RestController
@RequestMapping("/api/songquiz")
public class SongQuizController {

    private final SongQuizService songQuizService;

    public SongQuizController(SongQuizService songQuizService) {
        this.songQuizService = songQuizService;
    }

    @PostMapping("/submit")
    public SongQuiz submitQuiz(@RequestBody SongQuizDTO songQuizDTO) {
        return songQuizService.submitQuiz(songQuizDTO);
    }

    @GetMapping("/history/{userId}")
    public List<SongQuiz> getQuizHistory(@PathVariable Long userId) {
        return songQuizService.getUserQuizHistory(userId);
    }
}