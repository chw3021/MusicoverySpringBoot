package com.musicovery.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserSignupDTO;
import com.musicovery.user.entity.User;
import com.musicovery.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository = null;
	//private final PasswordEncoder passwordEncoder = null;

	@Override
	public User SignupUser(UserSignupDTO userSignupDTO) {
		if (userRepository.existsByEmail(userSignupDTO.getEmail())) {
			throw new IllegalStateException("이미 존재하는 이메일입니다.");
		}

		User user = User.builder()
				.email(userSignupDTO.getEmail())
				//.passwd(passwordEncoder.encode(userSignupDTO.getPasswd())) // 비밀번호 암호화
				.nickname(userSignupDTO.getNickname())
				.phone(userSignupDTO.getPhone())
				.address(userSignupDTO.getAddress())
				.isActive(true).build();

		return userRepository.save(user);
	}

	@Override
	public User login(UserDTO userDTO) {
		Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());

		if (optionalUser.isEmpty()) {
			throw new IllegalArgumentException("이메일이 존재하지 않습니다.");
		}

		User user = optionalUser.get();
//		if (!passwordEncoder.matches(userDTO.getPasswd(), user.getPasswd())) {
//			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//		}

		return user; // JWT 토큰 발급 로직 추가 가능
	}
}
