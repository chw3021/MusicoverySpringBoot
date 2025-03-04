package com.musicovery.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musicovery.admin.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
	// 특정 상태의 신고 목록 조회
	List<Report> findByStatus(String status);
}
