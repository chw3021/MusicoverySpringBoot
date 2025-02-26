package com.musicovery.userreport.entity;

import java.time.LocalDateTime;

import com.musicovery.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "report")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter; // 신고한 사용자

    @ManyToOne
    @JoinColumn(name = "reported_id", nullable = false)
    private User reportedUser; // 신고 대상 사용자

    @Column(nullable = false, length = 500)
    private String reason; // 신고 사유

    @Column(nullable = false)
    private LocalDateTime reportedAt; // 신고 일시

    @Column(nullable = false)
    private String status; // 상태 추가
}