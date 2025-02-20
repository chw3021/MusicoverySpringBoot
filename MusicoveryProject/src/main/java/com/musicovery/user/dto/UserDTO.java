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
public class UserDTO {

	private String userId;

	private String email;

	// JSON 응답에서 제외 (보안 목적)
	@JsonIgnore
	private String passwd;

}
