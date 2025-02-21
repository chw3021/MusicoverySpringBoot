package com.musicovery.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.user.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByEmail(String email); // 이메일로 회원 찾기
	Optional<User> findByUserId(String userId); // 이메일로 회원 찾기

	boolean existsByEmail(String email); // 이메일 중복 확인

	boolean existsByNickname(String nickname); // 닉네임 중복 확인
}
