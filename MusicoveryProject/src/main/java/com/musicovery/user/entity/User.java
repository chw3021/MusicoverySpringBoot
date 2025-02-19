package com.musicovery.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;


	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String passwd;

	@Column(unique = true, nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String phone;

	@Column(nullable = false)
	private String address;

	private String profileImageUrl;

	private String bio;

	@Column(nullable = false)
	private boolean spotifyConnected;

	@Column(nullable = false)
	private boolean googleConnected;

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
