package com.musicovery.userreport.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReportDTO {
    private Long reporterId;
    private Long reportedUserId;
    private String reason;
    private LocalDateTime reportedAt;
}