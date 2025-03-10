package com.musicovery.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private String id;

	private String userId;

	private String email;
	
	private String nickname;

	// JSON 응답에서 제외 (보안 목적)

	private String passwd;

	private boolean isActive;
	
	private boolean isAdmin;
}
