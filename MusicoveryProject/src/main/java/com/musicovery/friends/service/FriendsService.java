package com.musicovery.friends.service;

import com.musicovery.friends.dto.FriendsDTO;
import com.musicovery.friends.entity.Friends;

public interface FriendsService {

    Friends sendFriendRequest(FriendsDTO friendDTO);  // 친구 요청 보내기

    Friends acceptFriendRequest(Long requestId);  // 친구 요청 수락

    Friends declineFriendRequest(Long requestId);  // 친구 요청 거절
}