package com.musicovery.songquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicovery.gemini.service.GeminiService;
import com.musicovery.songquiz.dto.SongQuizDTO;
import com.musicovery.songquiz.entity.SongQuiz;

@Service
public class SongQuizService {
    private final GeminiService geminiService;

    @Autowired
    public SongQuizService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public SongQuizDTO getLyrics(SongQuiz songquiz) {
        String lyrics = geminiService.getLyrics(songquiz.getArtist(), songquiz.getTitle());
        return new SongQuizDTO(songquiz.getArtist(), songquiz.getTitle(), lyrics);
    }
}