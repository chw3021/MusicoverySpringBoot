package com.musicovery.admin.service;

import java.util.List;

import com.musicovery.user.entity.User;

//import com.musicovery.user.entity.User;
//import java.util.List;

public interface UserManagementService {

	User getUserById(String userId); // 특정 사용자 조회

	boolean toggleUserStatus(String userId); // 사용자 계정 활성화/비활성화

	boolean deleteUser(String userId); // 사용자 삭제

	List<User> getAllUsers(String searchTerm, String sortBy);

}
