package com.musicovery.friends.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.friends.entity.Friends;

public interface FriendsRepository extends JpaRepository<Friends, Long> {
    List<Friends> findByUserId(String userId);
    List<Friends> findByFriendIdAndIsAcceptedFalse(String friendId);
    List<Friends> findByUserIdAndIsAcceptedFalse(String userId);
    void deleteByUserIdAndFriendId(String userId, String friendId);
    void deleteByFriendIdAndUserId(String friendId, String userId);
}