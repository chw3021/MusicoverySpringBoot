package com.musicovery.admin.entity;

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
	private Long report_Id; // 신고 ID

	@Column(name = "reason", nullable = false, length = 255)
	private String reason; // 신고 사유

	@Column(name = "reported_id", nullable = false, length = 50)
	private String reportedUserId; // 신고된 사용자 (user_id)

	@Column(name = "reporter_id", nullable = false, length = 50)
	private String reporterUserId; // 신고한 사용자 (user_id)

	@Column(name = "status", nullable = false, length = 50)
	private String status; // 신고 상태 (대기, 처리 중, 완료)

	@Column(name = "reported_post_title", length = 255)
	private String reportedPostTitle;

	@Column(name = "reported_post_author", length = 100)
	private String reportedPostAuthor;

	@Column(name = "reported_post_content", columnDefinition = "TEXT")
	private String reportedPostContent;

	@Column(name = "playlist_title", length = 255)
	private String playlistTitle;

	@Column(name = "playlist_description", length = 500)
	private String playlistDescription;

	// ✅ 상태 업데이트 메서드 유지
	public void updateStatus(String newStatus) {
		this.status = newStatus;
	}
}
