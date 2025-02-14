package com.musicovery.admin.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notice")
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noticeId; // 공지사항 ID

	@Column(nullable = false, length = 255)
	private String title; // 공지 제목

	@Lob
	private String content; // 공지 내용

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt; // 생성 날짜

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
