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
@Table(name = "banned_user")
public class BannedUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false, unique = true)
	private String userId;

	@Column(name = "ban_reason", nullable = false, length = 255)
	private String banReason;

	@Column(name = "ban_end_date")
	private LocalDateTime banEndDate; // 정지 해제 날짜 (null이면 영구정지)

	public boolean isBanned() {
		return banEndDate == null || banEndDate.isAfter(LocalDateTime.now());
	}
}
