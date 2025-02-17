package com.musicovery.friends.entity;

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
@Table(name = "friends")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // 친구 요청을 보낸 사용자 ID

    @Column(nullable = false)
    private Long friendId; // 친구 요청을 받은 사용자 ID

    @Column(nullable = false)
    private boolean isAccepted; // 친구 요청 수락 여부
}