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

    @PostMapping("/report")
    public UserReport reportUser(@RequestBody UserReportDTO userReportDTO) {
        return userReportService.reportUser(userReportDTO);
    }

    @GetMapping("/reports/{reportedUserId}")
    public List<UserReport> getReportsByUser(@PathVariable Long reportedUserId) {
        return userReportService.getReportsByUser(reportedUserId);
    }
}