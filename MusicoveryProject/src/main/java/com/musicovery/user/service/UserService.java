package com.musicovery.user.service;

import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserSignupDTO;
import com.musicovery.user.entity.User;

public interface UserService {
	User SignupUser(UserSignupDTO userSignupDTO); // 회원가입

	User login(UserDTO userDTO); // 로그인

	User spotifyLogin(UserDTO userDTO);
}
