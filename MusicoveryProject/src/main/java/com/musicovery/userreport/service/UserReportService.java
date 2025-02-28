package com.musicovery.userreport.service;

import java.util.List;

import com.musicovery.userreport.dto.UserReportDTO;
import com.musicovery.userreport.entity.UserReport;

public interface UserReportService {

	List<UserReport> getReports();

	UserReport reportUser(UserReportDTO userReportDTO);

	List<UserReport> getReportsByReporter(String reporterUserId);

	List<UserReport> getReportsByReportedUser(String reportedUserId);

	UserReport updateUserReportStatus(Long reportId, String status);

	List<UserReportDTO> getUserReports();
}