package com.musicovery.friends.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicovery.friends.dto.FriendsDto;
import com.musicovery.friends.entity.Friends;
import com.musicovery.friends.repository.FriendsRepository;

@Service
public class FriendsServiceImpl implements FriendsService {

    private final FriendsRepository friendsRepository;

    public FriendsServiceImpl(FriendsRepository friendsRepository) {
        this.friendsRepository = friendsRepository;
    }

    @Override
    public Friends addFriend(FriendsDto friendsDto) {
        Friends friends = Friends.builder()
                .userId(friendsDto.getUserId())
                .friendId(friendsDto.getFriendId())
                .isAccepted(false)
                .build();
        return friendsRepository.save(friends);
    }

    @Override
    public List<Friends> getFriendsByUserId(Long userId) {
        return friendsRepository.findByUserId(userId);
    }
}