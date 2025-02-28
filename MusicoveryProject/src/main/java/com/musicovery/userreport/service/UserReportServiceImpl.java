package com.musicovery.userreport.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.musicovery.user.entity.User;
import com.musicovery.user.repository.UserRepository;
import com.musicovery.userreport.dto.UserReportDTO;
import com.musicovery.userreport.entity.UserReport;
import com.musicovery.userreport.repository.UserReportRepository;

@Service
public class UserReportServiceImpl implements UserReportService {

	private final UserReportRepository userReportRepository;
	private final UserRepository userRepository;

	public UserReportServiceImpl(UserReportRepository userReportRepository, UserRepository userRepository) {
		this.userReportRepository = userReportRepository;
		this.userRepository = userRepository;
	}

	@Override
	public UserReport reportUser(UserReportDTO userReportDTO) {
		// 신고자와 피신고자를 ID로 조회
		User reporter = userRepository.findById(userReportDTO.getReporter())
				.orElseThrow(() -> new IllegalArgumentException("신고자 ID가 존재하지 않습니다: " + userReportDTO.getReporter()));
		User reportedUser = userRepository.findById(userReportDTO.getReportedUser()).orElseThrow(
				() -> new IllegalArgumentException("피신고자 ID가 존재하지 않습니다: " + userReportDTO.getReportedUser()));

		// UserReport 엔티티 생성
		UserReport userReport = UserReport.builder().reporter(reporter).reportedUser(reportedUser)
				.reason(userReportDTO.getReason()).reportedAt(LocalDateTime.now()).status("신고 접수") // 초기 상태 설정
				.build();

		// 저장하고 반환
		return userReportRepository.save(userReport);
	}

	@Override
	public List<UserReport> getReportsByReporter(String reporterUserId) {
		return userReportRepository.findByReporter_Id(reporterUserId);
	}

	@Override
	public List<UserReport> getReportsByReportedUser(String reportedUserId) {
		return userReportRepository.findByReportedUser_Id(reportedUserId);
	}

	@Override
	public List<UserReport> getReports() {
		return userReportRepository.findAll();
	}

	// 신고 상태를 업데이트하는 메서드
	@Override
	public UserReport updateUserReportStatus(Long reportId, String status) {
		UserReport userReport = userReportRepository.findById(reportId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid report Id:" + reportId));

		userReport.setStatus(status);
		return userReportRepository.save(userReport);
	}

	@Override
	public List<UserReportDTO> getUserReports() {
		return userReportRepository.findAll().stream().map(report -> {
			UserReportDTO dto = new UserReportDTO();
			dto.setReporter(report.getReporter().getId()); // ID 그대로 유지
			dto.setReportedUser(report.getReportedUser().getId());
			dto.setReason(report.getReason());
			dto.setReportedAt(report.getReportedAt());
			dto.setStatus(report.getStatus());
			return dto;
		}).collect(Collectors.toList());
	}
}