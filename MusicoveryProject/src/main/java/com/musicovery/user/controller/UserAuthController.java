package com.musicovery.user.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.musicovery.mail.service.MailService;
import com.musicovery.user.dto.SpotifyUserDTO;
import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserProfileDTO;
import com.musicovery.user.dto.UserSignupDTO;
import com.musicovery.user.entity.User;
import com.musicovery.user.repository.UserRepository;
import com.musicovery.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserAuthController {

	private final UserService userService;
	private final MailService mailService;
	private final UserRepository userRepository;

//	@PostMapping("/signup")
//	public ResponseEntity<UserDTO> signup(@RequestBody UserSignupDTO userSignupDTO) {
//		UserDTO userDTO = userService.signup(userSignupDTO);
//		return ResponseEntity.ok(userDTO);
//	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserSignupDTO userSignupDTO) {
	    try {
	        UserDTO userDTO = userService.signup(userSignupDTO);
	        return ResponseEntity.ok(userDTO);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity
	                .status(HttpStatus.CONFLICT) // 409 Conflict
	                .body(Collections.singletonMap("message", e.getMessage()));
	    }
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
		UserDTO loggedInUser = userService.login(userDTO);
		return ResponseEntity.ok(loggedInUser);
	}

	
//	@GetMapping("/profile")
//    public Object getUserProfile(Authentication authentication, 
//                                 @AuthenticationPrincipal Object principal) {
//        if (authentication == null) {
//            return "인증되지 않은 사용자입니다.";
//        }
//
//        // 일반 로그인 사용자인 경우 (UserDetails)
//        if (principal instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) principal;
//            return Map.of(
//                "loginType", "LOCAL",
//                "username", userDetails.getUsername()
//            );
//        }
//
//        // OAuth2 로그인 사용자인 경우 (OAuth2User)
//        if (principal instanceof OAuth2User) {
//            OAuth2User oauth2User = (OAuth2User) principal;
//            return Map.of(
//                "loginType", "OAUTH2",
//                "attributes", oauth2User.getAttributes()
//            );
//        }
//
//        return "알 수 없는 로그인 방식입니다.";
//    }

	
//	@PutMapping("/profile/{Id}")
//	public ResponseEntity<UserDTO> updateProfile(
//	        @PathVariable("Id") String Id,  // 경로 변수와 일치시킴
//	        @RequestBody UserProfileDTO userProfileDTO) {
//	    UserDTO updatedUser = userService.updateProfile(Id, userProfileDTO);
//	    return ResponseEntity.ok(updatedUser);
//	}

	@PutMapping(value = "/profile/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<UserDTO> updateProfile(
	        @PathVariable("id") String id,
	        @RequestPart("userProfileDTO") UserProfileDTO userProfileDTO,  // JSON 데이터 받음
	        @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {

	    UserDTO updatedUser = userService.updateProfile(id, userProfileDTO, profileImage);
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
	    // 이메일 중복 확인
	    if (userRepository.existsByEmail(userSignupDTO.getEmail())) {
	        return ResponseEntity
	                .status(HttpStatus.CONFLICT) // 409 Conflict
	                .body(Collections.singletonMap("message", "이미 사용중인 이메일입니다."));
	    }

	    // 중복이 아니면 이메일 인증 토큰 생성 및 메일 발송
	    String token = userService.createVerificationToken(userSignupDTO.getEmail());
	    mailService.sendVerificationEmail(userSignupDTO.getEmail(), token);

	    return ResponseEntity.ok(Collections.singletonMap("message", "이메일 인증 메일이 발송되었습니다."));
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
	
	@GetMapping("/signup/check-nickname")
	public ResponseEntity<?> checkNickname(@RequestParam String nickname) {
	    boolean isDuplicate = userRepository.existsByNickname(nickname);

	    if (isDuplicate) {
	        return ResponseEntity
	                .status(HttpStatus.CONFLICT) // 409 Conflict
	                .body(Collections.singletonMap("message", "이미 사용중인 닉네임입니다."));
	    }
	    
	    return ResponseEntity.ok(Collections.singletonMap("message", "사용 가능한 닉네임입니다."));
	}
	
	@PostMapping("/profile")
    public ResponseEntity<UserProfileDTO> getUserProfile(@RequestBody UserProfileDTO userProfileDTO) {
        String id = userProfileDTO.getId(); // 요청 본문에서 id 추출
        UserProfileDTO userProfile = userService.getUserProfile(id);
        return ResponseEntity.ok(userProfile);
    }

}
