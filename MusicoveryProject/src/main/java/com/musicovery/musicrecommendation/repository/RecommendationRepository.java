package com.musicovery.musicrecommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.musicrecommendation.entity.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Recommendation.RecommendationPK> {
}
