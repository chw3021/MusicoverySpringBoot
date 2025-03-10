package com.musicovery.userreport.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserReportDTO {
	private Long id;
    private String reporterId;
    private String reportedUserId;
    private String reason;
    private LocalDateTime reportedAt;
    private String status;
	private Long postId;
}