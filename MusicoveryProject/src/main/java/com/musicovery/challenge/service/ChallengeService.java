package com.musicovery.challenge.service;

import java.util.List;

import com.musicovery.challenge.dto.ChallengeDTO;
import com.musicovery.challenge.entity.Challenge;

public interface ChallengeService {
    Challenge startChallenge(ChallengeDTO challengeDTO);
    List<Challenge> getUserChallenges(Long userId);
}