package com.musicovery.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicovery.admin.entity.Report;
import com.musicovery.admin.repository.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService {
	private final ReportRepository reportRepository;

	public ReportServiceImpl(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}

	@Override
	public List<Report> getAllReports() {
		return reportRepository.findAll();
	}

	@Override
	public List<Report> getReportsByStatus(String status) {
		return reportRepository.findByStatus(status);
	}

	@Override
	public boolean updateReportStatus(Long reportId, String status) {
		Report report = reportRepository.findById(reportId)
				.orElseThrow(() -> new RuntimeException("신고를 찾을 수 없습니다: " + reportId));
		report.updateStatus(status);
		reportRepository.save(report);
		return true;
	}
}
