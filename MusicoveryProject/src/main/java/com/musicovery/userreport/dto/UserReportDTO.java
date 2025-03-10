package com.musicovery.userreport.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserReportDTO {
	private Long id;
	private String reporter;
	private String reportedUser;
	private String reason;
	private LocalDateTime reportedAt;
	private String status;
	private Long postId;
	private String postTitle;
	private String postDescription;
	private Long reportId;
	private String reporterId;
	private String reportedUserId;
	private String reportedNickname;
}