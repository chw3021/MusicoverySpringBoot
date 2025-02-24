package com.musicovery.admin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.musicovery.user.entity.User;
import com.musicovery.admin.service.UserManagementService;
import com.musicovery.user.entity.User;

//import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class UserManagementController {
	private final UserManagementService userService;

	public UserManagementController(UserManagementService userService) {
		this.userService = userService;
	}

	// 사용자 목록 조회
	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	// 특정 사용자 조회
	@GetMapping("/{userId}")
	public User getUserById(@PathVariable String userId) {
		return userService.getUserById(userId);
	}

	// 사용자 계정 활성화/비활성화
	@PutMapping("/{userId}/status")
	public boolean toggleUserStatus(@PathVariable String userId) {
		return userService.toggleUserStatus(userId);
	}

	// 사용자 삭제
	@DeleteMapping("/{userId}")
	public boolean deleteUser(@PathVariable String userId) {
		return userService.deleteUser(userId);
	}
}
