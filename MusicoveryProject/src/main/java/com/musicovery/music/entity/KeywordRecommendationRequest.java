package com.musicovery.music.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeywordRecommendationRequest {
    private String genre;
    private Integer bpm;
    private String mood;
}
