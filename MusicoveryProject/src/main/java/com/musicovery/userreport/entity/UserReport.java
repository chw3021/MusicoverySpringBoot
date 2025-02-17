package com.musicovery.userreport.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_report")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reporterId; // 신고한 사용자 ID

    @Column(nullable = false)
    private Long reportedUserId; // 신고 대상 사용자 ID

    @Column(nullable = false, length = 500)
    private String reason; // 신고 사유

    @Column(nullable = false)
    private LocalDateTime reportedAt; // 신고 일시
}