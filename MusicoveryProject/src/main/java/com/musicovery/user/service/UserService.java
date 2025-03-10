package com.musicovery.user.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.musicovery.user.dto.SpotifyTokenDTO;
import com.musicovery.user.dto.SpotifyUserDTO;
import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserProfileDTO;
import com.musicovery.user.dto.UserSignupDTO;
import com.musicovery.user.dto.UserUpdateDTO;
import com.musicovery.user.entity.User;

public interface UserService {
	UserDTO signup(UserSignupDTO userSignupDTO); // 회원가입

	UserDTO login(UserDTO userDTO); // 로그인

	UserDTO updateProfile(String userId, UserProfileDTO userProfileDTO, MultipartFile profileImage);

	User spotifyLogin(SpotifyUserDTO userDTO);

	User findByUserId(String userId);

	// Authorization Code로 Access Token 요청
	SpotifyTokenDTO exchangeCodeForAccessToken(String authorizationCode);

	// Access Token으로 Spotify 사용자 정보 가져오기
	SpotifyUserDTO getSpotifyUserProfile(String accessToken);

	UserDTO spotifyLoginDTO(SpotifyUserDTO spotifyUserDTO);

	List<User> searchUsers(String keyword);

	String createVerificationToken(String email);

	void verifyEmail(String token);

	List<Long> getWeeklyUserCounts();

	long getTotalUsers(); // 총 유저 수 조회 메서드 추가

	List<User> getRecentUsers(); // 최근 가입한 유저 3명 조회
	
	UserProfileDTO getUserProfile(String id);

	User updateUserInfo(String userId, UserUpdateDTO userUpdateDTO);
	
	void deleteProfileImage(String id);
	
	void deleteUser(String id, String password);
	
	UserDTO getUserInfo(String id);
}
