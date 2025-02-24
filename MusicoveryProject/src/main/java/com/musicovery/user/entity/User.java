package com.musicovery.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
	@Id
	@Column(updatable = false, nullable = false, unique = true)
	private String id;
	
	// 스포티파이 api용 id
	@Column(nullable = false, unique = true)
	private String userId;

	@Column(unique = true, nullable = false)
	private String email;

	// 비밀번호가 없을 수 있으므로 nullable = true
	@Column(nullable = true)
	private String passwd;

	@Column(unique = true, nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String phone;

	private String address;

	private String profileImageUrl;

	private String bio;

	// 소셜 연동 여부 기본값 false
	@Column(nullable = false)
	@Builder.Default
	private boolean spotifyConnected = false;

	@Column(nullable = false)
	@Builder.Default
	private boolean googleConnected = false;

	@Column(nullable = false)
	@Builder.Default
	private boolean isActive = true;

	@Column(nullable = false, updatable = false)
	@Builder.Default
	private LocalDateTime regdate = LocalDateTime.now();

	@Column(nullable = false)
	@Builder.Default
	private LocalDateTime lastupdate = LocalDateTime.now();

	@PreUpdate
	public void preUpdate() {
		this.lastupdate = LocalDateTime.now();
	}
}
