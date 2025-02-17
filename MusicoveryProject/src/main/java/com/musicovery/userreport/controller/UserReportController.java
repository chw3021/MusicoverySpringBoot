package com.musicovery.userreport.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.userreport.dto.UserReportDTO;
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
        // DTO를 이용해 신고 처리
        return userReportService.reportUser(userReportDTO);
    }

    // 특정 사용자의 신고 내역을 조회하는 GET 요청
    @GetMapping("/reports/{reportedUserId}")
    public List<UserReport> getReportsByUser(@PathVariable String reportedUserId) {
        // 사용자의 신고 내역을 조회
        return userReportService.getReportsByUser(reportedUserId);
    }
}