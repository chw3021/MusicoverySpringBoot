package com.musicovery.musicrecommendation.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(Recommendation.RecommendationPK.class)
public class Recommendation {

    @Id
    @Column(nullable = false)
    private String userId;  // 사용자 ID

    @Id
    @Column(nullable = false)
    private String musicId;  // 음악 ID

    @Column(nullable = false)
    private Double score;  // 추천점수

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendationPK implements Serializable {
        /**
		 * 
		 */
		private static final long serialVersionUID = -1390660598156225134L;
		private String userId;
        private String musicId;
    }
}
