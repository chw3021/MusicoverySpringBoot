package com.musicovery.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.user.dto.SpotifyTokenDTO;
import com.musicovery.user.dto.SpotifyUserDTO;
import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserProfileDTO;
import com.musicovery.user.dto.UserSignupDTO;
import com.musicovery.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserAuthController {
	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<UserDTO> signup(@RequestBody UserSignupDTO userSignupDTO) {
		UserDTO userDTO = userService.signup(userSignupDTO);
		return ResponseEntity.ok(userDTO);
	}

	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
		UserDTO loggedInUser = userService.login(userDTO);
		return ResponseEntity.ok(loggedInUser);
	}

	@PutMapping("/profile/{userId}")
	public ResponseEntity<UserDTO> updateProfile(@PathVariable String userId,
			@RequestBody UserProfileDTO userProfileDTO) {

		UserDTO updatedUser = userService.updateProfile(userId, userProfileDTO);
		return ResponseEntity.ok(updatedUser);
	}

	// Spotify 로그인/회원가입
	@PostMapping("/spotify-login")
	public ResponseEntity<UserDTO> spotifyLogin(@RequestBody String authorizationCode) {
		try {
			// 1. Authorization Code로 Access Token 요청
			SpotifyTokenDTO tokenDTO = userService.exchangeCodeForAccessToken(authorizationCode);

			if (tokenDTO == null || tokenDTO.getAccessToken() == null) {
				return ResponseEntity.badRequest().build(); // 토큰 가져오기 실패 시 Bad Request 응답
			}

			String accessToken = tokenDTO.getAccessToken();

			// 2. Access Token으로 사용자 정보 가져오기
			SpotifyUserDTO spotifyUser = userService.getSpotifyUserProfile(accessToken);

			if (spotifyUser == null || spotifyUser.getEmail() == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 사용자 정보 가져오기 실패 시 Unauthorized 응답
			}

			// 3. 가져온 사용자 정보로 회원가입 또는 로그인 처리
			UserDTO userDTO = userService.spotifyLogin(spotifyUser);

			return ResponseEntity.ok(userDTO);

		} catch (Exception e) {
			// 예외 발생 시 Internal Server Error 응답
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 현재 로그인한 사용자 정보 가져오기
	/*
	 * @GetMapping("/{userId}") public ResponseEntity<User> getUser(@PathVariable
	 * String userId) { User user = userService.getUserById(userId); return
	 * ResponseEntity.ok(user); }
	 */

}
