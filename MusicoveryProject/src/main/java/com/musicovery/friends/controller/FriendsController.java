package com.musicovery.friends.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.friends.dto.FriendsDTO;
import com.musicovery.friends.entity.Friends;
import com.musicovery.friends.service.FriendsService;

@RestController
@RequestMapping("/api/friends")
public class FriendsController {

    private final FriendsService friendsService;

    public FriendsController(FriendsService friendService) {
        this.friendsService = friendService;
    }

    // 친구 요청 보내기
    @PostMapping("/request")
    public ResponseEntity<Friends> sendFriendRequest(@RequestBody FriendsDTO friendsDTO) {
        Friends friends = friendsService.sendFriendRequest(friendsDTO);
        return ResponseEntity.ok(friends);
    }

    // 친구 요청 수락
    @PutMapping("/accept/{requestId}")
    public ResponseEntity<Friends> acceptFriendRequest(@PathVariable Long requestId) {
        Friends friends = friendsService.acceptFriendRequest(requestId);
        return ResponseEntity.ok(friends);
    }

    // 친구 요청 거절
    @DeleteMapping("/decline/{requestId}")
    public ResponseEntity<Friends> declineFriendRequest(@PathVariable Long requestId) {
        Friends friends = friendsService.declineFriendRequest(requestId);
        return ResponseEntity.ok(friends);
    }
}