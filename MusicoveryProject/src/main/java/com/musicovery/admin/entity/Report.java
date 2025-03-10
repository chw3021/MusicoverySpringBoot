package com.musicovery.admin.entity;

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
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "report")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_id") // ✅ 명확한 컬럼 매핑 추가
	private Long reportId;

	@Column(name = "reported_id", nullable = false, length = 50)
	private String reportedUserId;

	@Column(name = "reporter_id", nullable = false, length = 50)
	private String reporterUserId;

	@Column(name = "reason", nullable = false, length = 255)
	private String reason;

	@Column(name = "status", nullable = false, length = 50)
	private String status;

	@Column(name = "reported_post_title", length = 255)
	private String reportedPostTitle;

	@Column(name = "reported_post_author", length = 100)
	private String reportedPostAuthor;

	@Column(name = "reported_post_content", columnDefinition = "TEXT")
	private String reportedPostContent;

	@Column(name = "ban_end_date")
	private LocalDateTime banEndDate;

	public void updateStatus(String newStatus) {
		this.status = newStatus;
	}

	public boolean isBanned() {
		return banEndDate == null || banEndDate.isAfter(LocalDateTime.now());
	}
}
