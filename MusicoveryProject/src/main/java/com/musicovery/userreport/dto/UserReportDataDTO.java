package com.musicovery.userreport.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserReportDataDTO {
	private Long id;
    private String reporterId;
    private String reportedUserId;
    private String reporterNickname;
    private String reportedUserNickname;
    private String reason;
    private LocalDateTime reportedAt;
    private String status;
	private Long postId;
    private String postTitle;
    private String postDescription;
}