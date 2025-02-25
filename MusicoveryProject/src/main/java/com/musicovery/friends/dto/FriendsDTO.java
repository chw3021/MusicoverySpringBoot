package com.musicovery.friends.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendsDTO {

    private String userId;    // 요청한 사용자 ID
    private String friendId;  // 친구 추가된 사용자 ID
    
}