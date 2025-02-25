package com.musicovery.friends.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.friends.entity.Friends;

public interface FriendsRepository extends JpaRepository<Friends, Long> {
    List<Friends> findByUserId(String userId);
    List<Friends> findByFriendId(String friendId);
}