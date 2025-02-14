package com.musicovery.spotifyapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    private String tokenType;
    private int expiresIn;
}
