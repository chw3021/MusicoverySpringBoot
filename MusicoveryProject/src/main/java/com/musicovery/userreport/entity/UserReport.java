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
@Table(name = "report")  // 데이터베이스 테이블 이름에 맞게 수정
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")  // 필드 이름을 데이터베이스에 맞게 수정
    private Long id;

    @Column(name = "reporter_id", nullable = false) // String으로 수정
    private String reporterId; // 신고한 사용자 ID

    @Column(name = "reported_id", nullable = false)  // String으로 수정
    private String reportedUserId; // 신고 대상 사용자 ID

    @Column(nullable = false, length = 500)
    private String reason; // 신고 사유

    @Column(nullable = false)
    private LocalDateTime reportedAt; // 신고 일시

    @Column(nullable = false)
    private String status;  // 상태 추가
}