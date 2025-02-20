package com.musicovery.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.user.dto.SpotifyUserDTO;
import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserProfileDTO;
import com.musicovery.user.dto.UserSignupDTO;
import com.musicovery.user.entity.User;
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
	public ResponseEntity<UserDTO> updateProfile(
	        @PathVariable String userId, 
	        @RequestBody UserProfileDTO userProfileDTO) {
	    
	    UserDTO updatedUser = userService.updateProfile(userId, userProfileDTO);
	    return ResponseEntity.ok(updatedUser);
	}
	
	// Spotify 로그인/회원가입
	@PostMapping("/spotify-login")
	public ResponseEntity<User> spotifyLogin(@RequestBody SpotifyUserDTO userDTO) {
	    User user = userService.spotifyLogin(userDTO);
	    return ResponseEntity.ok(user);
	}
}
