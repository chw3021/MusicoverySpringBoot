package com.musicovery.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {

	private String id;
	
	private String nickname;

	private String profileImageUrl;

	private String bio;
}
