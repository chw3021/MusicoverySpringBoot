package com.musicovery.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSocialDTO {
	
	private boolean spotifyConnected;
	
	private boolean googleConnected;
}