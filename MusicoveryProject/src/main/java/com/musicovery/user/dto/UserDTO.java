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
	
	private String userId;
	
	private String email;
	
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
