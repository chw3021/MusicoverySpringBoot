package com.musicovery.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicovery.challenge.dto.ChallengeDTO;
import com.musicovery.challenge.entity.Challenge;
import com.musicovery.challenge.repository.ChallengeRepository;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;

    public ChallengeServiceImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public Challenge startChallenge(ChallengeDTO challengeDTO) {
        Challenge challenge = Challenge.builder()
                .userId(challengeDTO.getUserId())
                .genre(challengeDTO.getGenre())
                .goal(challengeDTO.getGoal())
                .progress(challengeDTO.getProgress())
                .isCompleted(challengeDTO.isCompleted())
                .build();
        return challengeRepository.save(challenge);
    }

    @Override
    public List<Challenge> getUserChallenges(Long userId) {
        return challengeRepository.findByUserId(userId);
    }
}