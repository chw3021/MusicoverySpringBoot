package com.musicovery.friends.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.friends.entity.Friends;

public interface FriendsRepository extends JpaRepository<Friends, Long> {
    List<Friends> findByUserId(Long userId);
    List<Friends> findByUserIdAndIsAcceptedTrue(Long userId);//수락된친구만 조회
}