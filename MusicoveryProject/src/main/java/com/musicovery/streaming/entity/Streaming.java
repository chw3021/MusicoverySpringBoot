package com.musicovery.streaming.entity;

import com.musicovery.playlist.entity.Playlist;
import com.musicovery.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @OneToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User hostUser;

    @Column(name = "is_live", nullable = false)
    private Boolean isLive;

    @Column(name = "is_premium_only", nullable = false)
    private Boolean isPremiumOnly;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;
}