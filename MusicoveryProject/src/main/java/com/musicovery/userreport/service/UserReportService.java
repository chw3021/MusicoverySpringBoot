package com.musicovery.userreport.service;

import java.util.List;

import com.musicovery.userreport.dto.UserReportDTO;
import com.musicovery.userreport.dto.UserReportDataDTO;
import com.musicovery.userreport.entity.UserReport;

public interface UserReportService {

	List<UserReportDataDTO> getReports();

	UserReport reportUser(UserReportDTO userReportDTO);

	List<UserReportDataDTO> getReportsByReporter(String reporterUserId);

	List<UserReportDataDTO> getReportsByReportedUser(String reportedUserId);

	UserReport updateUserReportStatus(Long reportId, String status);

	List<UserReportDTO> getUserReports();
}