package com.musicovery.challenge.entity;

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
@Table(name = "challenge")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // 챌린지를 진행한 사용자 ID

    @Column(nullable = false)
    private String genre; // 챌린지의 음악 장르

    @Column(nullable = false)
    private int goal; // 목표 청취 횟수

    @Column(nullable = false)
    private int progress; // 현재 청취 횟수

    @Column(nullable = false)
    private boolean isCompleted; // 챌린지 완료 여부
}