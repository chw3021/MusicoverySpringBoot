package com.musicovery.spotifyapi.service;

public interface SpotifyApiUserService {
    /**
     * 로그인한 사용자 정보를 가져옵니다.
     * @return 사용자 정보 (userId 포함)
     */
    String getUserInfo(String accessToken);
}
