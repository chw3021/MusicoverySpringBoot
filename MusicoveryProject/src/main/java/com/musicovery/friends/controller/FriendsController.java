package com.musicovery.friends.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.friends.dto.FriendsDto;
import com.musicovery.friends.entity.Friends;
import com.musicovery.friends.service.FriendsService;

@RestController
@RequestMapping("/api/friends")
public class FriendsController {

    private final FriendsService friendsService;

    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @PostMapping("/add")
    public Friends addFriend(@RequestBody FriendsDto friendsDTO) {
        return friendsService.addFriend(friendsDTO);
    }

    @GetMapping("/{userId}")
    public List<Friends> getFriends(@PathVariable Long userId) {
        return friendsService.getFriendsByUserId(userId);
    }
}