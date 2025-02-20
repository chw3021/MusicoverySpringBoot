package com.musicovery.user.service;

import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserProfileDTO;
import com.musicovery.user.dto.UserSignupDTO;

public interface UserService {
	UserDTO signup(UserSignupDTO userSignupDTO); // 회원가입

	UserDTO login(UserDTO userDTO); // 로그인

	UserDTO updateProfile(String userId, UserProfileDTO userProfileDTO);
}
