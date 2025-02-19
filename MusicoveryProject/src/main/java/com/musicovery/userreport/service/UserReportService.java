package com.musicovery.userreport.service;

import java.util.List;

import com.musicovery.userreport.dto.UserReportDTO;
import com.musicovery.userreport.entity.UserReport;

public interface UserReportService {
    UserReport reportUser(UserReportDTO userReportDTO);
    
    List<UserReport> getReportsByUser(String reportedUserId);
}