package com.musicovery.admin.service;

import com.musicovery.admin.entity.Report;
import java.util.List;

public interface ReportService {
    List<Report> getAllReports();
    List<Report> getReportsByStatus(String status);
    boolean updateReportStatus(Long reportId, String status);
}
