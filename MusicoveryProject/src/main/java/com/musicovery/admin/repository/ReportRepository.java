package com.musicovery.admin.repository;

import com.musicovery.admin.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    // 특정 상태의 신고 목록 조회
    List<Report> findByStatus(String status);
}
