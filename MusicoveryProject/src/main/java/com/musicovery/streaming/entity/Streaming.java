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

@Entity
@Table(name = "streaming")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Streaming {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long hostUserId; // 스트리밍 호스트 사용자 ID

    @Column(nullable = false)
    private String playlistName; // 스트리밍 플레이리스트 이름

    @Column(nullable = false)
    private boolean isLive; // 현재 스트리밍 중 여부

    @Column(nullable = false)
    private boolean isPremiumOnly; // Premium 계정 여부
}