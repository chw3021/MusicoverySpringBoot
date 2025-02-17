package com.musicovery.musicrecommendation.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;  // 사용자 ID

    @Column(nullable = false)
    private Integer musicId;  // 음악 ID

    @Column(nullable = false)
    private Integer weightAmount;  // 가중치

    // 복합키 설정: user_id + music_id
    @Embeddable
    public static class WeightPK implements java.io.Serializable {
        private String userId;
        private Integer musicId;
    }
}
