package com.musicovery.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserSignupDTO;
import com.musicovery.user.entity.User;
import com.musicovery.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	// 생성자 주입
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User SignupUser(UserSignupDTO userSignupDTO) {
		if (userRepository.existsByEmail(userSignupDTO.getEmail())) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}

		User user = User.builder()
				.email(userSignupDTO.getEmail())
				.passwd(passwordEncoder.encode(userSignupDTO.getPasswd())) // 비밀번호 암호화
				.nickname(userSignupDTO.getNickname())
				.phone(userSignupDTO.getPhone())
				.address(userSignupDTO.getAddress())
				.isActive(true)
				.build();

		return userRepository.save(user);
	}

	@Override
	public User login(UserDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));

		if (userDTO.getPasswd() == null || !passwordEncoder.matches(userDTO.getPasswd(), user.getPasswd())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		return user; // JWT 토큰 발급 로직 추가 가능
	}
}
