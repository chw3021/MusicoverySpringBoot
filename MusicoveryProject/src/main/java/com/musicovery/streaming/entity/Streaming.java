package com.musicovery.streaming.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "streaming")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // ✅ Lombok Builder 추가!
public class Streaming {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "playlist_name", nullable = false)
    private String playlistName;

    @Column(name = "host_user_id", nullable = false)
    private String hostUserId;

    @Column(name = "is_live", nullable = false)
    private boolean isLive;

    @Column(name = "is_premium_only", nullable = false)
    private boolean isPremiumOnly;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;
    
    @Column(name = "nickname", nullable = false)
    private String nickname;
}