package com.musicovery.userreport.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.userreport.dto.UserReportDTO;
import com.musicovery.userreport.dto.UserReportDataDTO;
import com.musicovery.userreport.entity.UserReport;
import com.musicovery.userreport.service.UserReportService;

@RestController
@RequestMapping("/api/userreport")
public class UserReportController {

	private final UserReportService userReportService;

	public UserReportController(UserReportService userReportService) {
		this.userReportService = userReportService;
	}

	// 사용자를 신고하는 POST 요청
	@PostMapping("/report")
	public UserReport reportUser(@RequestBody UserReportDTO userReportDTO) {
		return userReportService.reportUser(userReportDTO);
	}

	// 특정 사용자가 신고한 내역을 조회하는 GET 요청
	@GetMapping("/reporter/{reporterUserId}")
	public List<UserReportDataDTO> getReportsByReporter(@PathVariable String reporterUserId) {
		return userReportService.getReportsByReporter(reporterUserId);
	}

	// 특정 사용자가 신고당한 내역을 조회하는 GET 요청
	@GetMapping("/reported/{reportedUserId}")
	public List<UserReportDataDTO> getReportsByReportedUser(@PathVariable String reportedUserId) {
		return userReportService.getReportsByReportedUser(reportedUserId);
	}

	// 전체 신고 조회
	@GetMapping("/reportsAll")
	public List<UserReportDataDTO> getReports() {
		return userReportService.getReports();
	}

	// 신고 상태를 업데이트하는 PUT 요청
	@PutMapping("/status/{reportId}")
	public ResponseEntity<?> updateUserReportStatus(@PathVariable Long reportId,
			@RequestBody Map<String, String> request) {
		String status = request.get("status");

		if (status == null || status.isEmpty()) {
			return ResponseEntity.badRequest().body("🚨 상태 값이 비어 있습니다.");
		}

		try {
			UserReport updatedReport = userReportService.updateUserReportStatus(reportId, status);
			return ResponseEntity.ok(updatedReport);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// 신고 목록 조회 (DTO 기반)
	@GetMapping("/reports")
	public List<UserReportDTO> getUserReports() {
		return userReportService.getUserReports();
	}

	// UserReportController.java (컨트롤러)
	@GetMapping("/count")
	public int getReportCount() {
		return userReportService.countUserReports();
	}

}
