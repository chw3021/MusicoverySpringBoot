package com.musicovery.userreport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.userreport.entity.UserReport;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {
    // reportedUserId가 String 타입으로 수정되어야 함
    List<UserReport> findByReportedUserId(String reportedUserId);
}