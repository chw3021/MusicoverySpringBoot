package com.musicovery.songquiz.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicovery.songquiz.entity.Ranking;
import com.musicovery.songquiz.repository.RankingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final RankingRepository rankingRepository;

    public Ranking saveRanking(Ranking ranking) {
        return rankingRepository.save(ranking);
    }

    public List<Ranking> getRankings() {
        return rankingRepository.findAllByOrderByTimeTakenAsc();
    }

    // top 5 랭킹을 가져오도록 추가
    public List<Ranking> getTop5Rankings() {
        return rankingRepository.findTop5ByOrderByTimeTakenAsc();
    }
}