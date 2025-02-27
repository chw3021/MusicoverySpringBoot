package com.musicovery.user.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.musicovery.friends.entity.Friends;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
	@Column(nullable = true, unique = true)
	private String userId;

	@Column(unique = true, nullable = false)
	private String email;

	// 비밀번호가 없을 수 있으므로 nullable = true
	@Column(nullable = false)
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
//	@Builder.Default
	private boolean isActive;

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
	
	@PrePersist
	public void prePersist() {
		// userId가 null일 경우 UUID를 생성하여 할당
		if (id == null) {
			id = UUID.randomUUID().toString();
		}
	}
	
	@Column(nullable = false)
	@Builder.Default
	private boolean isAdmin = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Friends> friends;

    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Friends> friendOf;
}
