package com.musicovery.challenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeDTO {
    private Long userId;
    private String genre;
    private int goal;
    private int progress;
    private boolean isCompleted;
}