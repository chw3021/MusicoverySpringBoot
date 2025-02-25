package com.musicovery.friends.service;

import java.util.List;

import com.musicovery.friends.entity.Friends;

public interface FriendsService {
    Friends addFriend(String userId, String friendId);
    List<Friends> getFriends(String userId);
    List<Friends> getFriendOf(String friendId);
    Friends acceptFriendRequest(Long friendRequestId);
}