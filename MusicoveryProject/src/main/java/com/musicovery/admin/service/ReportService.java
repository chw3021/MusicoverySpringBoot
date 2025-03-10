package com.musicovery.admin.service;

import java.util.List;

import com.musicovery.admin.entity.Report;

public interface ReportService {
	List<Report> getAllReports();

	List<Report> getReportsByStatus(String status);

	boolean updateReportStatus(Long reportId, String status);

	boolean isUserBanned(String reportedUserId);

	boolean unbanUser(String reportedUserId);

	boolean banUser(Long reportId, int days);
}
