package com.musicovery.admin.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicovery.admin.entity.Report;
import com.musicovery.admin.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

	private final ReportRepository reportRepository;

	@Override
	public List<Report> getAllReports() {
		return reportRepository.findAll();
	}

	@Override
	public List<Report> getReportsByStatus(String status) {
		return reportRepository.findByStatus(status);
	}

	@Override
	@Transactional
	public boolean updateReportStatus(Long reportId, String status) {
		Optional<Report> reportOpt = reportRepository.findById(reportId);
		if (reportOpt.isPresent()) {
			Report report = reportOpt.get();
			report.updateStatus(status);
			reportRepository.save(report);
			return true;
		}
		return false;
	}

	@Override
	public boolean banUser(Long reportId, int days) {
		// reportId로 신고 내역 조회
		Optional<Report> reportOpt = reportRepository.findById(reportId);

		if (reportOpt.isPresent()) {
			Report report = reportOpt.get();
			report.updateStatus("BANNED"); // 상태 업데이트
			reportRepository.save(report);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean unbanUser(String reportedUserId) {
		List<Report> bans = reportRepository.findAllByReportedUserId(reportedUserId);
		if (bans.isEmpty()) {
			return false;
		}
		for (Report ban : bans) {
			ban.updateStatus("정지 해제됨");
			reportRepository.save(ban);
		}
		return true;
	}

	@Override
	public boolean isUserBanned(String reportedUserId) {
		return reportRepository.findByReportedUserIdAndBanEndDateAfter(reportedUserId, LocalDateTime.now()).isPresent();
	}
}
