package com.musicovery.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musicovery.admin.entity.BannedUser;

@Repository
public interface BannedUserRepository extends JpaRepository<BannedUser, Long> {
	Optional<BannedUser> findByUserId(String userId);
}
