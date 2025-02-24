package com.musicovery.songquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.songquiz.entity.SongQuiz;

public interface SongQuizRepository extends JpaRepository<SongQuiz, Long> {
    //List<SongQuiz> findByUserId(String userId);
}