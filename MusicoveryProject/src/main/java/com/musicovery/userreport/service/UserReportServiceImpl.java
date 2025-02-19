package com.musicovery.userreport.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.musicovery.userreport.dto.UserReportDTO;
import com.musicovery.userreport.entity.UserReport;
import com.musicovery.userreport.repository.UserReportRepository;

@Service
public class UserReportServiceImpl implements UserReportService {

    private final UserReportRepository userReportRepository;

    public UserReportServiceImpl(UserReportRepository userReportRepository) {
        this.userReportRepository = userReportRepository;
    }

    @Override
    public UserReport reportUser(UserReportDTO userReportDTO) {
        UserReport userReport = UserReport.builder()
                .reporterId(userReportDTO.getReporterId())          // String으로 수정
                .reportedUserId(userReportDTO.getReportedUserId())  // String으로 수정
                .reason(userReportDTO.getReason())
                .reportedAt(LocalDateTime.now()) // 신고 시간 설정
                .status("Pending")   // 기본 상태 "Pending" 설정
                .build();
        return userReportRepository.save(userReport);
    }

    @Override
    public List<UserReport> getReportsByUser(String reportedUserId) {
        return userReportRepository.findByReportedUserId(reportedUserId);  // String으로 수정
    }
}