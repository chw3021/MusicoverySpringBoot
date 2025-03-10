package com.musicovery.user.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.musicovery.file.service.FileStorageService;
import com.musicovery.mail.entity.EmailVerificationToken;
import com.musicovery.mail.repository.EmailVerificationTokenRepository;
import com.musicovery.mail.service.MailService;
import com.musicovery.user.dto.SpotifyTokenDTO;
import com.musicovery.user.dto.SpotifyUserDTO;
import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserProfileDTO;
import com.musicovery.user.dto.UserSignupDTO;
import com.musicovery.user.dto.UserUpdateDTO;
import com.musicovery.user.entity.User;
import com.musicovery.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper; // ModelMapper 주입
	private final EmailVerificationTokenRepository tokenRepository;
	private final MailService mailService;
	private final FileStorageService fileService;

	// 생성자 주입
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper,
			EmailVerificationTokenRepository tokenRepository, MailService mailService, FileStorageService fileService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.modelMapper = modelMapper;
		this.tokenRepository = tokenRepository;
		this.fileService = fileService;
		this.mailService = mailService;
	}

	@Override
	public UserDTO signup(UserSignupDTO userSignupDTO) {
		// 이미 존재하는 이메일인지 확인
		if (userRepository.existsByEmail(userSignupDTO.getEmail())) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}

		// 이미 존재하는 닉네임인지 확인
		if (userRepository.existsByNickname(userSignupDTO.getNickname())) {
			throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
		}

		// DTO -> 엔티티
		User user = modelMapper.map(userSignupDTO, User.class);

		// UUID로 ID 생성
		user.setId(UUID.randomUUID().toString());

		// 비밀번호 암호화
		if (userSignupDTO.getPasswd() != null) {
			user.setPasswd(passwordEncoder.encode(userSignupDTO.getPasswd()));
		}

		// 사용자 저장
		User savedUser = userRepository.save(user);

		// 엔티티 -> DTO
		return modelMapper.map(savedUser, UserDTO.class);
	}

	@Override
	public UserDTO login(UserDTO userDTO) {
		// 이메일로 유저 조회
		User user = userRepository.findByEmail(userDTO.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));

		// isActive 필드 체크 (0이면 로그인 실패)
		if (!user.isActive()) {
			throw new IllegalArgumentException("비활성화된 계정입니다. 관리자에게 문의하세요.");
		}

		// 비밀번호 일치 확인
		if (userDTO.getPasswd() == null || !passwordEncoder.matches(userDTO.getPasswd(), user.getPasswd())) {
			log.error("비밀번호 불일치 - 이메일: {}", userDTO.getEmail());
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		// 로그인 성공 로그
		log.info("로그인 성공 - 이메일: {}", userDTO.getEmail());

		// 엔티티 → DTO
		return modelMapper.map(user, UserDTO.class);
	}

//	@Override
//	public UserDTO updateProfile(String userId, UserProfileDTO userProfileDTO) {
//		// 유저 정보 확인
//		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
//
//		// 닉네임이 변경되었고, 중복일 경우 예외 발생
//		if (!user.getNickname().equals(userProfileDTO.getNickname())
//				&& userRepository.existsByNickname(userProfileDTO.getNickname())) {
//			throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
//		}
//
//		// 변경할 필드만 업데이트
//		user.setNickname(userProfileDTO.getNickname());
//		user.setProfileImageUrl(userProfileDTO.getProfileImageUrl());
//		user.setBio(userProfileDTO.getBio());
//
//		User updatedUser = userRepository.save(user);
//
//		// 엔티티 → DTO
//		return modelMapper.map(updatedUser, UserDTO.class);
//	}

	@Override
	public UserDTO updateProfile(String userId, UserProfileDTO userProfileDTO, MultipartFile profileImage) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		if (!user.getNickname().equals(userProfileDTO.getNickname())
				&& userRepository.existsByNickname(userProfileDTO.getNickname())) {
			throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
		}

		user.setNickname(userProfileDTO.getNickname());
		user.setBio(userProfileDTO.getBio());

		// 프로필 이미지 업로드
		if (profileImage != null && !profileImage.isEmpty()) {
			try {
				String profileImageUrl = fileService.uploadFile(profileImage); // 새로운 메서드 사용
				user.setProfileImageUrl(profileImageUrl);
			} catch (IOException e) {
				throw new RuntimeException("파일 업로드 실패", e);
			}
		}

		User updatedUser = userRepository.save(user);
		return modelMapper.map(updatedUser, UserDTO.class);
	}

	@Override
	public User findByUserId(String userId) {
		return userRepository.findByUserId(userId)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
	}

	// oauth 2.0 로그인 및 회원가입
	@Override
	public UserDTO spotifyLoginDTO(SpotifyUserDTO spotifyUserDTO) {
		// 이메일로 기존 유저 확인
		User user = userRepository.findByEmail(spotifyUserDTO.getEmail()).orElseGet(() -> {
			// 기존 유저가 없다면 신규 가입
			User newUser = User.builder().id(spotifyUserDTO.getId()).userId(spotifyUserDTO.getUserId())
					.email(spotifyUserDTO.getEmail())
					// 비밀번호 암호화 추가
					.passwd(passwordEncoder.encode(spotifyUserDTO.getPasswd())).nickname(spotifyUserDTO.getNickname())
					.bio(spotifyUserDTO.getBio()).phone(spotifyUserDTO.getPhone())
					.profileImageUrl(spotifyUserDTO.getProfileImageUrl()).spotifyConnected(true).isActive(true) // 새로
																												// 생성되는
																												// 유저는
																												// 활성화
																												// 상태
					.build();
			return userRepository.save(newUser);
		});

		// 기존 계정이 있지만 아직 소셜 연동이 안 되어 있는 경우
		if (user != null && !user.isSpotifyConnected() && user.getUserId() == null) {
			user.setUserId(spotifyUserDTO.getUserId()); // Spotify ID 추가
			user.setSpotifyConnected(true); // 소셜 연동 활성화
			userRepository.save(user); // 업데이트된 정보 저장
		}

		// isActive 필드 체크 (0이면 로그인 실패)
		if (!user.isActive()) {
			throw new IllegalArgumentException("비활성화된 계정입니다. 관리자에게 문의하세요.");
		}

		// ModelMapper를 사용하여 엔티티 -> DTO 변환
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public User spotifyLogin(SpotifyUserDTO userDTO) {
		return userRepository.findByUserId(userDTO.getUserId()).orElseGet(() -> {
			User newUser = User.builder().userId(userDTO.getUserId()).email(userDTO.getEmail())
					.passwd(passwordEncoder.encode(userDTO.getPasswd())).bio(userDTO.getBio()).phone(userDTO.getPhone())
					.nickname(userDTO.getNickname()).profileImageUrl(userDTO.getProfileImageUrl())
					.spotifyConnected(true).isActive(true).build();
			return userRepository.save(newUser);
		});
	}

	@Value("${spotify.client.id}")
	private String clientId;

	@Value("${spotify.client.secret}")
	private String clientSecret;

	@Value("${spotify.api.redirect_uri}")
	private String redirectUri;

	@Override
	public SpotifyTokenDTO exchangeCodeForAccessToken(String code) {
		String tokenUrl = "https://accounts.spotify.com/api/token";

		// HTTP 요청 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// HTTP 요청 바디 설정
		Map<String, String> params = new HashMap<>();
		params.put("grant_type", "authorization_code");
		params.put("code", code);
		params.put("redirect_uri", redirectUri);
		params.put("client_id", clientId);
		params.put("client_secret", clientSecret);

		// 요청 보내기
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);

		try {
			URI uri = new URI(tokenUrl);
			SpotifyTokenDTO response = restTemplate.postForObject(uri, request, SpotifyTokenDTO.class);
			return response;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SpotifyUserDTO getSpotifyUserProfile(String accessToken) {
		String userProfileUrl = "https://api.spotify.com/v1/me";

		// HTTP 요청 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// 요청 보내기
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(headers);

		try {
			ResponseEntity<SpotifyUserDTO> response = restTemplate.exchange(userProfileUrl, HttpMethod.GET, request,
					SpotifyUserDTO.class);
			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> searchUsers(String keyword) {
		return userRepository.findByEmailContainingOrUserIdContainingOrNicknameContaining(keyword, keyword, keyword);
	}

	// 이메일 인증을 위한 토큰 생성
	public String createVerificationToken(String email) {
		String token = UUID.randomUUID().toString();
		EmailVerificationToken verificationToken = new EmailVerificationToken();
		verificationToken.setToken(token);
		verificationToken.setEmail(email);
		verificationToken.setExpirationTime(LocalDateTime.now().plusHours(24)); // 24시간 유효
		tokenRepository.save(verificationToken);
		return token;
	}

	// 이메일 인증 처리
	public void verifyEmail(String token) {
		EmailVerificationToken verificationToken = tokenRepository.findByToken(token)
				.orElseThrow(() -> new IllegalArgumentException("잘못된 인증 토큰입니다."));

		if (verificationToken.getExpirationTime().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("인증 토큰이 만료되었습니다.");
		}

		User user = userRepository.findByEmail(verificationToken.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		userRepository.save(user);
	}

	@Override
	public List<Long> getWeeklyUserCounts() {
		List<Long> weeklyCounts = new ArrayList<>();
		LocalDate today = LocalDate.now();

		for (int i = 6; i >= 0; i--) { // 최신 날짜가 마지막에 위치하도록 정렬
			LocalDate date = today.minusDays(i);
			long count = userRepository.countUsersByJoinDate(date);
			weeklyCounts.add(count);
		}

		return weeklyCounts;
	}

	@Override
	public long getTotalUsers() {
		long totalUsers = userRepository.count();
		log.info("총 유저 수 조회: {}", totalUsers);
		return totalUsers;
	}

	@Override
	public List<User> getRecentUsers() {
		return userRepository.findTop3ByOrderByRegdateDesc();
	}

	@Override
	public UserProfileDTO getUserProfile(String id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

		return new UserProfileDTO(user.getId(), user.getNickname(), user.getProfileImageUrl(), user.getBio());
	}

	// 아이디에 해당하는 사용자 정보 수정
	public User updateUserInfo(String userId, UserUpdateDTO userUpdateDTO) {
		// 1. 사용자 조회
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

		// 2. 비밀번호 변경이 있다면, 비밀번호 암호화 후 업데이트
		if (userUpdateDTO.getPasswd() != null && !userUpdateDTO.getPasswd().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(userUpdateDTO.getPasswd());
			user.setPasswd(encodedPassword);
		}

		// 3. 전화번호 및 주소 변경
		if (userUpdateDTO.getPhone() != null) {
			user.setPhone(userUpdateDTO.getPhone());
		}
		if (userUpdateDTO.getAddress() != null) {
			user.setAddress(userUpdateDTO.getAddress());
		}

		// 4. 변경된 사용자 정보 저장
		return userRepository.save(user);
	}

	@Override
	public void deleteProfileImage(String id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

		// 프로필 이미지 필드 null로 업데이트
		user.setProfileImageUrl(null);
		userRepository.save(user);
	}


	@Transactional
	@Override
	public void deleteUser(String id, String password) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

		// 비밀번호 검증
		if (!passwordEncoder.matches(password, user.getPasswd())) {
			throw new RuntimeException("비밀번호가 올바르지 않습니다.");
		}

		// 유저 삭제
		userRepository.delete(user);

    }

	@Override
	public UserDTO getUserInfo(String id) {
		User user = userRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	        // User 엔티티에서 필요한 정보를 DTO로 변환
	        UserDTO userDTO = new UserDTO();
	        userDTO.setId(user.getId());
	        userDTO.setAdmin(user.isAdmin());  // admin 여부 전달
	        return userDTO;
	}
}
