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


	
	
	// Authorization Code로 Access Token 요청
	SpotifyTokenDTO exchangeCodeForAccessToken(String authorizationCode);

	// Access Token으로 Spotify 사용자 정보 가져오기
	SpotifyUserDTO getSpotifyUserProfile(String accessToken);

	UserDTO spotifyLoginDTO(SpotifyUserDTO spotifyUserDTO);

}
