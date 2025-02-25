package com.musicovery.friends.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.friends.entity.Friends;
import com.musicovery.friends.service.FriendsService;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    private final FriendsService friendsService;

    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @PostMapping("/add")
    public Friends addFriend(@RequestParam String userId, @RequestParam String friendId) {
        return friendsService.addFriend(userId, friendId);
    }

    @GetMapping("/list")
    public List<Friends> getFriends(@RequestParam String userId) {
        return friendsService.getFriends(userId);
    }

    @GetMapping("/friendOf")
    public List<Friends> getFriendOf(@RequestParam String friendId) {
        return friendsService.getFriendOf(friendId);
    }

    @PostMapping("/accept")
    public Friends acceptFriendRequest(@RequestParam Long friendRequestId) {
        return friendsService.acceptFriendRequest(friendRequestId);
    }
}