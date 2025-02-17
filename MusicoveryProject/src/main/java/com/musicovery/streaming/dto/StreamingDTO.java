package com.musicovery.streaming.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StreamingDTO {
    private Long hostUserId;
    private String playlistName;
    private boolean isLive;
    private boolean isPremiumOnly;
}