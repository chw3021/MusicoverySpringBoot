package com.musicovery.songquiz.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicovery.songquiz.dto.SongQuizDTO;
import com.musicovery.songquiz.entity.SongQuiz;
import com.musicovery.songquiz.repository.SongQuizRepository;

@Service
public class SongQuizServiceImpl implements SongQuizService {

    private final SongQuizRepository songQuizRepository;

    public SongQuizServiceImpl(SongQuizRepository songQuizRepository) {
        this.songQuizRepository = songQuizRepository;
    }

    @Override
    public SongQuiz submitQuiz(SongQuizDTO songQuizDto) {
        SongQuiz songQuiz = SongQuiz.builder()
                .userId(songQuizDto.getUserId())
                .question(songQuizDto.getQuestion())
                .correctAnswer(songQuizDto.getCorrectAnswer())
                .isCorrect(songQuizDto.isCorrect())
                .build();
        return songQuizRepository.save(songQuiz);
    }

    @Override
    public List<SongQuiz> getUserQuizHistory(Long userId) {
        return songQuizRepository.findByUserId(userId);
    }
}