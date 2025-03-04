package com.musicovery.admin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.admin.entity.Report;
import com.musicovery.admin.service.ReportService;

@RestController
@RequestMapping("/admin/reports")
public class ReportController {
	private final ReportService reportService;

	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	// 신고 목록 조회
	@GetMapping
	public List<Report> getAllReports() {
		List<Report> reports = reportService.getAllReports();

		// ✅ 로그 확인 (데이터가 정상적으로 포함되는지 확인)
		for (Report report : reports) {
			System.out.println("🚀 신고된 게시글 제목: " + report.getReportedPostTitle());
			System.out.println("🚀 신고된 플레이리스트 제목: " + report.getPlaylistTitle());
		}

		return reports;
	}

	// 특정 상태의 신고 조회
	@GetMapping("/status/{status}")
	public List<Report> getReportsByStatus(@PathVariable String status) {
		return reportService.getReportsByStatus(status);
	}

	// 신고 상태 변경
	@PutMapping("/{reportId}/status")
	public boolean updateReportStatus(@PathVariable Long reportId, @RequestParam String status) {
		return reportService.updateReportStatus(reportId, status);
	}

	@PutMapping("/{reportId}/resolve")
	public boolean resolveReport(@PathVariable Long reportId) {
		return reportService.updateReportStatus(reportId, "완료");
	}

	@PutMapping("/{reportId}/ban")
	public boolean banUser(@PathVariable Long reportId, @RequestParam int days) {
		return reportService.banUser(reportId, days);
	}

}
