package com.musicovery.userreport.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReportDTO {
    private String reporter;
    private String reportedUser;
    private String reason;
    private LocalDateTime reportedAt;
    private String status;
    private Long postId;
}