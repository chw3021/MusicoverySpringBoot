package com.musicovery.spotifyapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyApiRequestDTO {
    private String url;    // API 요청 URL
    private String method; // GET, POST 등 HTTP 메서드
}
