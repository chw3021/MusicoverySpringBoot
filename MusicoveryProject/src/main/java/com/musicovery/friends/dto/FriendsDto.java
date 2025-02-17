package com.musicovery.friends.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendsDto {
    private Long userId;
    private Long friendId;
    private boolean isAccepted;
}