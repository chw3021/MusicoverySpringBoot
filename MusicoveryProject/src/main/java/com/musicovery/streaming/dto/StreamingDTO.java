package com.musicovery.streaming.dto;

import com.musicovery.user.entity.User;

import lombok.Data;

@Data
public class StreamingDTO {
    private Long id;
    private String playlistId;
    private User hostUser;
    private boolean isLive;
    private boolean isPremiumOnly;
    private boolean isPublic;
    //
}