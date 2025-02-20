package com.musicovery.streaming.dto;

import lombok.Data;

@Data
public class StreamingDTO {
    private Long id;
    private String playlistName;
    private String hostUserId;
    private boolean isLive;
    private boolean isPremiumOnly;
    private boolean isPublic;
}