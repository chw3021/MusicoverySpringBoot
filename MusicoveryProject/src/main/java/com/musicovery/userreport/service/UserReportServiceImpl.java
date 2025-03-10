package com.musicovery.userreport.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicovery.post.entity.PlaylistPost;
import com.musicovery.post.repository.PlaylistPostRepository;
import com.musicovery.user.entity.User;
import com.musicovery.user.repository.UserRepository;
import com.musicovery.userreport.dto.UserReportDTO;
import com.musicovery.userreport.dto.UserReportDataDTO;
import com.musicovery.userreport.entity.UserReport;
import com.musicovery.userreport.repository.UserReportRepository;

@Service
public class UserReportServiceImpl implements UserReportService {

	private final UserReportRepository userReportRepository;
	private final UserRepository userRepository;
	private final PlaylistPostRepository playlistPostRepository;

	public UserReportServiceImpl(UserReportRepository userReportRepository, UserRepository userRepository,
			PlaylistPostRepository playlistPostRepository) {
		this.userReportRepository = userReportRepository;
		this.userRepository = userRepository;
		this.playlistPostRepository = playlistPostRepository;
	}

	private UserReportDataDTO convertToDataDTO(UserReport report) {
		UserReportDataDTO dto = new UserReportDataDTO();

		dto.setId(report.getId());
		dto.setReporterId(report.getReporter().getId());
		dto.setReporterNickname(report.getReporter().getNickname());
		dto.setReportedUserId(report.getReportedUser().getId());
		dto.setReportedUserNickname(report.getReportedUser().getNickname()); // 오류 수정
		dto.setReason(report.getReason());
		dto.setReportedAt(report.getReportedAt());
		dto.setStatus(report.getStatus());
		dto.setPostId(report.getPost().getId());
		dto.setPostTitle(report.getPost().getTitle());
		dto.setPostDescription(report.getPost().getDescription());

		return dto;
	}

	@Override
	public UserReport reportUser(UserReportDTO userReportDTO) {
		// 신고자와 피신고자를 ID로 조회
		User reporter = userRepository.findById(userReportDTO.getReporterId())
				.orElseThrow(() -> new IllegalArgumentException("신고자 ID가 존재하지 않습니다: " + userReportDTO.getReporterId()));
		User reportedUser = userRepository.findById(userReportDTO.getReportedUserId()).orElseThrow(
				() -> new IllegalArgumentException("피신고자 ID가 존재하지 않습니다: " + userReportDTO.getReportedUserId()));

		PlaylistPost post = playlistPostRepository.findById(userReportDTO.getPostId())
				.orElseThrow(() -> new IllegalArgumentException("Post ID가 존재하지 않습니다: " + userReportDTO.getPostId()));

		// UserReport 엔티티 생성
		UserReport userReport = UserReport.builder().reporter(reporter).reportedUser(reportedUser)
				.reason(userReportDTO.getReason()).post(post).reportedAt(LocalDateTime.now()).status("신고 접수") // 초기 상태
																												// 설정
				.build();

		// 저장하고 반환
		return userReportRepository.save(userReport);
	}

	public List<UserReportDataDTO> getReportsByReporter(String reporterUserId) {
		List<UserReport> reports = userReportRepository.findByReporter_Id(reporterUserId);
		return reports.stream().map(this::convertToDataDTO).collect(Collectors.toList());
	}

	@Override
	public List<UserReportDataDTO> getReportsByReportedUser(String reportedUserId) {
		List<UserReport> reports = userReportRepository.findByReportedUser_Id(reportedUserId);
		return reports.stream().map(this::convertToDataDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<UserReportDataDTO> getReports() {
		List<UserReport> reports = userReportRepository.findAll();
		return reports.stream().map(this::convertToDataDTO).collect(Collectors.toList());
	}

	// 신고 상태를 업데이트하는 메서드
	@Override
	public UserReport updateUserReportStatus(Long reportId, String status) {
		UserReport userReport = userReportRepository.findById(reportId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid report Id: " + reportId));

		userReport.setStatus(status);
		return userReportRepository.save(userReport);
	}

	@Override
	public List<UserReportDTO> getUserReports() {
	    return userReportRepository.findAllWithPost().stream().map(report -> {
	        UserReportDTO dto = new UserReportDTO();
	        dto.setReportId(report.getId());

	        // ✅ 신고자 정보 (ID + 닉네임)
	        dto.setReporter(report.getReporter() != null ? report.getReporter().getId() : "알 수 없음");

	        // ✅ 피신고자 정보 (ID + 닉네임)
	        dto.setReportedUser(report.getReportedUser().getId());
	        dto.setReportedNickname(report.getReportedUser().getNickname()); // 🚀 닉네임 추가

	        dto.setReason(report.getReason());
	        dto.setReportedAt(report.getReportedAt());
	        dto.setStatus(report.getStatus());

	        // 🚀 게시글 정보 추가
	        if (report.getPost() != null) {
	            dto.setPostId(report.getPost().getId());
	            dto.setPostTitle(report.getPost().getTitle());
	            dto.setPostDescription(report.getPost().getDescription());
	        }

	        return dto;
	    }).collect(Collectors.toList());
	}

	@Override
	public int countUserReports() {
	    return (int) userReportRepository.count();
	}

}
