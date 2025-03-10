package com.musicovery.admin.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musicovery.admin.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

	// 특정 상태의 신고 목록 조회
	List<Report> findByStatus(String status);

	// 신고된 사용자가 존재하는지 확인
	boolean existsByReportedUserId(String reportedUserId);

	// 특정 사용자의 정지 여부 확인
	Optional<Report> findByReportedUserIdAndBanEndDateAfter(String reportedUserId, LocalDateTime now);

	// 특정 사용자의 정지 기록 조회 (필요 시)
	List<Report> findAllByReportedUserId(String reportedUserId);

	boolean existsByReportedUserIdAndStatus(String userId, String string);
}
