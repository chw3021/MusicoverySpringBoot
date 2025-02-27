package com.musicovery.friends.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.friends.dto.FriendsDTO;
import com.musicovery.friends.entity.Friends;
import com.musicovery.friends.service.FriendsService;
import com.musicovery.user.entity.User;
import com.musicovery.user.service.UserService;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    private final FriendsService friendsService;
    private final UserService userService;

    public FriendsController(FriendsService friendsService, UserService userService) {
        this.friendsService = friendsService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public Friends addFriend(@RequestBody FriendsDTO friendsDTO) {
        return friendsService.addFriend(friendsDTO.getUserId(), friendsDTO.getFriendId());
    }

    @GetMapping("/list")
    public List<Friends> getFriends(@RequestParam String userId) {
        return friendsService.getFriends(userId);
    }

    @GetMapping("/friendRequests")
    public List<Friends> getFriendOf(@RequestParam String friendId) {
        return friendsService.getFriendRequests(friendId);
    }

    @GetMapping("/pendingRequests")
    public List<Friends> getPendingRequests(@RequestParam String userId) {
        return friendsService.getPendingRequests(userId);
    }


    @PostMapping("/accept")
    public Friends acceptFriendRequest(@RequestParam Long friendRequestId) {
        return friendsService.acceptFriendRequest(friendRequestId);
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String keyword) {
        return userService.searchUsers(keyword);
    }

    @DeleteMapping("/delete")
    public void deleteFriend(@RequestParam String userId, @RequestParam String friendId) {
        friendsService.deleteFriend(userId, friendId);
    }
}