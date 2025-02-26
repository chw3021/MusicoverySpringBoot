package com.musicovery.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyUserDTO {
    private String id;
    private String userId;
    private String email;
    private String passwd;
    private String profileImageUrl;
    private String bio;
    private String nickname;
    private String phone;
    private boolean isActive = true;
    private boolean spotifyConnected;
    private boolean googleConnected;

    // 🔥 인증 코드를 받기 위한 필드 추가
    private String code;
}
