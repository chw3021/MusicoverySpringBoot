package com.musicovery.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.mail.service.MailService;
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
	private final MailService mailService;

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
	public ResponseEntity<UserDTO> spotifyLogin(@RequestBody SpotifyUserDTO spotifyUserDTO) {
		UserDTO userDTO = userService.spotifyLoginDTO(spotifyUserDTO);
		return ResponseEntity.ok(userDTO);
	}

	@PostMapping("/signup/verify-email")
	public ResponseEntity<?> registerUser(@RequestBody UserSignupDTO userSignupDTO) {
		// 이메일 인증 토큰 생성
		String token = userService.createVerificationToken(userSignupDTO.getEmail());

		// 이메일로 인증 메일 전송
		mailService.sendVerificationEmail(userSignupDTO.getEmail(), token);

		return ResponseEntity.ok("이메일 인증 메일이 발송되었습니다.");
	}

	// 이메일 인증 검증
	@GetMapping("/verify-email")
	public ResponseEntity<?> verifyEmail(@RequestParam String token) {
		try {
			userService.verifyEmail(token);
			return ResponseEntity.ok("이메일 인증 성공!");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 링크입니다.");
		}
	}

	@GetMapping("/weekly-users")
	public List<Long> getWeeklyUserCounts() {
		return userService.getWeeklyUserCounts();
	}

	@GetMapping("/count")
	public ResponseEntity<Long> getTotalUsers() {
		long totalUsers = userService.getTotalUsers();
		return ResponseEntity.ok(totalUsers);
	}

	@GetMapping("/recent-users")
	public ResponseEntity<List<User>> getRecentUsers() {
		List<User> recentUsers = userService.getRecentUsers();
		return ResponseEntity.ok(recentUsers);
	}

}
