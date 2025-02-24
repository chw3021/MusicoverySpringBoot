package com.musicovery.user.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSignupDTO extends UserDTO {

	private String nickname;

	private String phone;

	private String address;

	private boolean isActive = true;

	public UserSignupDTO() {
		super();
		this.setId(UUID.randomUUID().toString());
	}
}
