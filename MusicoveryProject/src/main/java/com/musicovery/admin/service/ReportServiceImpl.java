package com.musicovery.admin.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicovery.admin.entity.BannedUser;
import com.musicovery.admin.entity.Report;
import com.musicovery.admin.repository.BannedUserRepository;
import com.musicovery.admin.repository.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService {
	private final ReportRepository reportRepository;
	private BannedUserRepository bannedUserRepository;

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

	public boolean createReport(String reason, String reportedUserId, String reporterUserId, String status,
			String reportedPostTitle, String reportedPostAuthor, String reportedPostContent) {
		Report report = Report.builder().reason(reason).reportedUserId(reportedUserId).reporterUserId(reporterUserId)
				.status(status).reportedPostTitle(reportedPostTitle) // ✅ 게시글 제목 추가
				.reportedPostAuthor(reportedPostAuthor) // ✅ 게시글 작성자 추가
				.reportedPostContent(reportedPostContent) // ✅ 게시글 본문 추가
				.build();

		reportRepository.save(report);
		return true;
	}

	@Autowired
	public ReportServiceImpl(ReportRepository reportRepository, BannedUserRepository bannedUserRepository) {
		this.reportRepository = reportRepository;
		this.bannedUserRepository = bannedUserRepository;
	}

	// ✅ 유저 정지 처리 메서드 추가
	@Override
	public boolean banUser(Long reportId, int days) {
		Report report = reportRepository.findById(reportId)
				.orElseThrow(() -> new RuntimeException("신고를 찾을 수 없습니다: " + reportId));

		String userId = report.getReportedUserId();
		LocalDateTime banEndDate = days > 0 ? LocalDateTime.now().plusDays(days) : null;

		BannedUser bannedUser = BannedUser.builder().userId(userId).banReason(report.getReason()).banEndDate(banEndDate)
				.build();

		bannedUserRepository.save(bannedUser);
		report.updateStatus("완료");
		reportRepository.save(report);
		return true;
	}
}
