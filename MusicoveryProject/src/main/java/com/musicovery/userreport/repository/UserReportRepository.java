package com.musicovery.userreport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.musicovery.userreport.entity.UserReport;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {
	List<UserReport> findByReportedUser_Id(String reportedUserId);

	List<UserReport> findByReporter_Id(String reportedUserId);

	@Query("SELECT r FROM UserReport r LEFT JOIN FETCH r.post")
	List<UserReport> findAllWithPost();

}