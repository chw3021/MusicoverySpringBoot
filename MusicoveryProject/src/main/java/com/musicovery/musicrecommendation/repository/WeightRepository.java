package com.musicovery.musicrecommendation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.musicrecommendation.entity.Weight;

public interface WeightRepository extends JpaRepository<Weight, Long> {
    
    // userId와 musicId로 가중치 조회
    Optional<Weight> findByUserIdAndMusicId(String userId, Integer musicId);

    // 가중치 업데이트가 필요하면 업데이트 쿼리 작성
    Weight save(Weight weight);
}
