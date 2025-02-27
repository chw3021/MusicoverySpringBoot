package com.musicovery.songquiz.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 랭킹의 고유 ID (자동 생성됨)
    
    private String username; // 정답을 맞힌 사용자의 이름 (또는 닉네임)
    private String songTitle; // 사용자가 맞힌 노래의 제목
    private long timeTaken; // AI 음성 재생부터 사용자가 정답을 입력하기까지 걸린 시간 (밀리초 단위)
    private LocalDateTime createdAt; // 정답을 맞힌 시간 (자동 생성됨)
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}