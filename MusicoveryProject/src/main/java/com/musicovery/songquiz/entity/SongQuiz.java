package com.musicovery.songquiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "song_quiz")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // 퀴즈를 진행한 사용자 ID

    @Column(nullable = false)
    private String question; // 퀴즈 질문 (노래 관련)

    @Column(nullable = false)
    private String correctAnswer; // 정답

    @Column(nullable = false)
    private boolean isCorrect; // 정답 여부
}