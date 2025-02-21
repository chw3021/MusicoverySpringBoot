package com.musicovery.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.musicovery.user.dto.SpotifyUserDTO;
import com.musicovery.user.dto.UserDTO;
import com.musicovery.user.dto.UserProfileDTO;
import com.musicovery.user.dto.UserSignupDTO;
import com.musicovery.user.entity.User;
import com.musicovery.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper; // ModelMapper 주입

	// 생성자 주입
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserDTO signup(UserSignupDTO userSignupDTO) {
		if (userRepository.existsByEmail(userSignupDTO.getEmail())) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}

		if (userRepository.existsByNickname(userSignupDTO.getNickname())) {
			throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
		}

		User user = modelMapper.map(userSignupDTO, User.class); // DTO → 엔티티
		user.setPasswd(passwordEncoder.encode(userSignupDTO.getPasswd())); // 비밀번호 암호화

		User savedUser = userRepository.save(user);

		return modelMapper.map(savedUser, UserDTO.class); // 엔티티 → DTO
	}

	@Override
	public UserDTO login(UserDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));

		if (userDTO.getPasswd() == null || !passwordEncoder.matches(userDTO.getPasswd(), user.getPasswd())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		return modelMapper.map(user, UserDTO.class); // 엔티티 → DTO
	}

	@Override
	public UserDTO updateProfile(String userId, UserProfileDTO userProfileDTO) {
		// 유저 정보 확인
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		// 닉네임이 변경되었고, 중복일 경우 예외 발생
		if (!user.getNickname().equals(userProfileDTO.getNickname())
				&& userRepository.existsByNickname(userProfileDTO.getNickname())) {
			throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
		}

		// 변경할 필드만 업데이트
		user.setNickname(userProfileDTO.getNickname());
		user.setProfileImageUrl(userProfileDTO.getProfileImageUrl());
		user.setBio(userProfileDTO.getBio());

		User updatedUser = userRepository.save(user);

		// 엔티티 → DTO
		return modelMapper.map(updatedUser, UserDTO.class);
	}
	

    @Override
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

	@Override
	public User spotifyLogin(SpotifyUserDTO userDTO) {
		return userRepository.findById(userDTO.getUserId()).orElseGet(() -> {
			User newUser = User.builder().userId(userDTO.getUserId()).email(userDTO.getEmail())
					.passwd(passwordEncoder.encode(userDTO.getPasswd())) // 비밀번호 설정
					.address(userDTO.getAddress()).bio(userDTO.getBio()).phone(userDTO.getPhone())
					.nickname(userDTO.getNickname()).profileImageUrl(userDTO.getProfileImageUrl())
					.spotifyConnected(true).isActive(true).build();
			return userRepository.save(newUser);
		});
	}
	
	
	
	

 
	//채팅방구현위해 추가   
	
  
 
}
