package com.musicovery.userreport.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReportDTO {
    private String reporterId;      // String으로 수정
    private String reportedUserId;  // String으로 수정
    private String reason;
    private LocalDateTime reportedAt;
    private String status;          // status 필드 추가
}