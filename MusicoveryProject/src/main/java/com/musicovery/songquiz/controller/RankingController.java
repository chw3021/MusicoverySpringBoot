package com.musicovery.songquiz.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.songquiz.entity.Ranking;
import com.musicovery.songquiz.service.RankingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @PostMapping
    public ResponseEntity<Ranking> saveRanking(@RequestBody Ranking ranking) {
        Ranking savedRanking = rankingService.saveRanking(ranking);
        return ResponseEntity.ok(savedRanking);
    }

    @GetMapping
    public ResponseEntity<List<Ranking>> getRankings() {
        return ResponseEntity.ok(rankingService.getRankings());
    }

    @GetMapping("/top5")
    public ResponseEntity<List<Ranking>> getTop5Ranking() {
        return ResponseEntity.ok(rankingService.getTop5Rankings());
    }
}