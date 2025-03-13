package com.musicovery.friends.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.musicovery.friends.entity.Friends;
import com.musicovery.user.entity.User;

public interface FriendsRepository extends JpaRepository<Friends, Long> {
    List<Friends> findByUserId(String userId);
    List<Friends> findByFriendIdAndIsAcceptedFalse(String friendId);
    List<Friends> findByUserIdAndIsAcceptedFalse(String userId);
    List<Friends> findByUserIdAndIsAcceptedTrue(String userId);
    void deleteByUserIdAndFriendId(String userId, String friendId);
    void deleteByFriendIdAndUserId(String friendId, String userId);

    void deleteAllByUserOrFriend(User user, User friend);

    @Query("SELECT f FROM Friends f WHERE (f.user.id = :userId OR f.friend.id = :userId) AND f.isAccepted = true")
    List<Friends> findByUserIdOrFriendIdAndIsAcceptedTrue(@Param("userId") String userId);
}