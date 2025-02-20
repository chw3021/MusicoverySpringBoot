package com.musicovery.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.user.dto.SpotifyUserDTO;
import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserSignupDTO;
import com.musicovery.user.entity.User;
import com.musicovery.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserAuthController {
	private final UserService userService;

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody UserSignupDTO userSignupDTO) {
		User user = userService.SignupUser(userSignupDTO);
		return ResponseEntity.ok(user);
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody UserDTO userDTO) {
		User user = userService.login(userDTO);
		return ResponseEntity.ok(user);
	}
	
//	// Spotify 로그인/회원가입
	@PostMapping("/spotify-login")
	public ResponseEntity<User> spotifyLogin(@RequestBody SpotifyUserDTO userDTO) {
	    User user = userService.spotifyLogin(userDTO);
	    return ResponseEntity.ok(user);
	}
}
