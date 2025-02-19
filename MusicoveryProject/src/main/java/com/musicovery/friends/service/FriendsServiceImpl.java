package com.musicovery.friends.service;

import org.springframework.stereotype.Service;

import com.musicovery.friends.dto.FriendsDTO;
import com.musicovery.friends.entity.Friends;
import com.musicovery.friends.repository.FriendsRepository;


@Service
public class FriendsServiceImpl implements FriendsService {

    private final FriendsRepository friendRepository;

    public FriendsServiceImpl(FriendsRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public Friends sendFriendRequest(FriendsDTO friendsDTO) {
        // 친구 요청 보내기: isAccepted는 기본적으로 false
        Friends friend = Friends.builder()
                .userId(friendsDTO.getUserId())
                .friendId(friendsDTO.getFriendId())
                .isAccepted(false)
                .build();
        return friendRepository.save(friend);
    }

    @Override
    public Friends acceptFriendRequest(Long requestId) {
        // 친구 요청 수락
        Friends friends = friendRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("친구 요청을 찾을 수 없습니다."));
        friends.setIsAccepted(true);
        return friendRepository.save(friends);
    }

    @Override
    public Friends declineFriendRequest(Long requestId) {
        // 친구 요청 거절
        Friends friends = friendRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("친구 요청을 찾을 수 없습니다."));
        friendRepository.delete(friends);
        return friends;
    }
}