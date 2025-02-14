package com.musicovery.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.admin.entity.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}
