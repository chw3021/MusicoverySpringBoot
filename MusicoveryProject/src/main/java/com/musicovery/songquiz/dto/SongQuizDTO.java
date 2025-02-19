package com.musicovery.songquiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongQuizDTO {
    private Long userId;
    private String question;
    private String correctAnswer;
    private boolean isCorrect;
}