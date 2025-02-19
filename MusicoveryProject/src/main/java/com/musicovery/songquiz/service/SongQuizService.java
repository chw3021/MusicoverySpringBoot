package com.musicovery.songquiz.service;

import java.util.List;

import com.musicovery.songquiz.dto.SongQuizDTO;
import com.musicovery.songquiz.entity.SongQuiz;

public interface SongQuizService {
    SongQuiz submitQuiz(SongQuizDTO songQuizDTO);
    List<SongQuiz> getUserQuizHistory(Long userId);
}