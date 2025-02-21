package com.musicovery.user.service;

import com.musicovery.user.dto.SpotifyUserDTO;
import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserProfileDTO;
import com.musicovery.user.dto.UserSignupDTO;
import com.musicovery.user.entity.User;

public interface UserService {
	UserDTO signup(UserSignupDTO userSignupDTO); // 회원가입

	UserDTO login(UserDTO userDTO); // 로그인
	
	UserDTO updateProfile(String userId, UserProfileDTO userProfileDTO);

	User spotifyLogin(SpotifyUserDTO userDTO);

	User findByUserId(String userId);

	 //채팅방구현 위해 추가

}
