package com.musicovery.challenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.challenge.entity.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByUserId(Long userId);
}