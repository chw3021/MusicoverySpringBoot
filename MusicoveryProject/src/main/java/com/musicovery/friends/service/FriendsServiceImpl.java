package com.musicovery.friends.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.musicovery.friends.entity.Friends;
import com.musicovery.friends.repository.FriendsRepository;
import com.musicovery.user.entity.User;


@Service
public class FriendsServiceImpl implements FriendsService {

    private final FriendsRepository friendsRepository;

    public FriendsServiceImpl(FriendsRepository friendsRepository) {
        this.friendsRepository = friendsRepository;
    }

    @Override
    public Friends addFriend(String userId, String friendId) {
        Friends friend = Friends.builder()
                .user(User.builder().id(userId).build())
                .friend(User.builder().id(friendId).build())
                .isAccepted(false)
                .build();
        return friendsRepository.save(friend);
    }

    @Override
    public List<Friends> getFriends(String userId) {
        return friendsRepository.findByUserId(userId);
    }

    @Override
    public List<Friends> getFriendOf(String friendId) {
        return friendsRepository.findByFriendId(friendId);
    }

    @Override
    public Friends acceptFriendRequest(Long friendRequestId) {
        Optional<Friends> friendRequest = friendsRepository.findById(friendRequestId);
        if (friendRequest.isPresent()) {
            Friends friend = friendRequest.get();
            friend.setIsAccepted(true);
            return friendsRepository.save(friend);
        }
        throw new RuntimeException("친구 요청을 찾을 수 없습니다.");
    }
}