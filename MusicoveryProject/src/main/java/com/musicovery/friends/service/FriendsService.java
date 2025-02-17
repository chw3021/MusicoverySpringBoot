package com.musicovery.friends.service;

import java.util.List;

import com.musicovery.friends.dto.FriendsDto;
import com.musicovery.friends.entity.Friends;

public interface FriendsService {
    Friends addFriend(FriendsDto friendsDto);
    List<Friends> getFriendsByUserId(Long userId);
}