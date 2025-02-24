package com.musicovery.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musicovery.user.entity.User;
import com.musicovery.user.repository.UserRepository;

@Service
public class UserManagementServiceImpl implements UserManagementService {
	private final UserRepository userRepository;

	public UserManagementServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(String userId) {
		return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId));
	}

	@Override
	@Transactional
	public boolean toggleUserStatus(String userId) {
		User user = getUserById(userId);
		user.setActive(!user.isActive()); // 계정 상태 토글
		userRepository.save(user);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteUser(String userId) {
		if (!userRepository.existsById(userId)) {
			return false;
		}
		userRepository.deleteById(userId);
		return true;
	}
}
