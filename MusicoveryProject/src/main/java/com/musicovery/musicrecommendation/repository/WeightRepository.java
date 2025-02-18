package com.musicovery.musicrecommendation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.musicrecommendation.entity.Weight;

public interface WeightRepository extends JpaRepository<Weight, Weight.WeightPK> {

    // userId와 musicId로 가중치 조회
    Optional<Weight> findByUserIdAndMusicId(String userId, String musicId);

    // 가중치 업데이트 또는 저장
    Weight save(Weight weight);
}
