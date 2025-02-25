package com.musicovery.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.user.entity.User;


public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<User> findByNickname(String nickname);

    // 사용자 검색 메서드 추가
    List<User> findByEmailContainingOrUserIdContainingOrNicknameContaining(String email, String userId, String nickname);
}