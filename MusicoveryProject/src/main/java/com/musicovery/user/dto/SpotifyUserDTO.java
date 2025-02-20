package com.musicovery.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyUserDTO {
	private String userId;

	private String email;

	// JSON 응답에서 제외 (보안 목적)
	@JsonIgnore
	private String passwd;
	
	private String profileImageUrl;

	private String bio;
	
	private String nickname;

	private String phone;

	private String address;

	private boolean isActive = true;
	
	private boolean spotifyConnected;
	
	private boolean googleConnected;
}
