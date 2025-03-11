package com.musicovery.songquiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musicovery.songquiz.entity.Ranking;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    List<Ranking> findAllByOrderByTimeTakenAsc();
    List<Ranking> findTop5ByOrderByTimeTakenAsc();
}