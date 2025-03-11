package com.musicovery.user.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.musicovery.user.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByEmail(String email);

	Optional<User> findByUserId(String userId);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

	Optional<User> findByNickname(String nickname);

	// 사용자 검색 메서드 추가
	List<User> findByEmailContainingOrUserIdContainingOrNicknameContaining(String email, String userId, String nickname,
			Sort sort);

	// 날짜별 가입한 유저 수 조회
	@Query("SELECT COUNT(u) FROM User u WHERE DATE(u.regdate) = :date")
	long countUsersByJoinDate(@Param("date") LocalDate date);

	@Query("SELECT COUNT(u) FROM User u WHERE u.regdate BETWEEN :startDate AND :endDate")
	long countUsersByCreatedAtBetween(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

	@Query("SELECT u FROM User u ORDER BY u.regdate DESC LIMIT 3")
	List<User> findTop3ByOrderByRegdateDesc();

}