package com.musicovery.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupDTO extends UserDTO {

	private String nickname;

	private String phone;

	private String address;

	private boolean isActive = true;

}
