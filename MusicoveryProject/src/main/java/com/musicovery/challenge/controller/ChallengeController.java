package com.musicovery.challenge.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.challenge.dto.ChallengeDTO;
import com.musicovery.challenge.entity.Challenge;
import com.musicovery.challenge.service.ChallengeService;

@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping("/start")
    public Challenge startChallenge(@RequestBody ChallengeDTO challengeDTO) {
        return challengeService.startChallenge(challengeDTO);
    }

    @GetMapping("/history/{userId}")
    public List<Challenge> getUserChallenges(@PathVariable Long userId) {
        return challengeService.getUserChallenges(userId);
    }
}