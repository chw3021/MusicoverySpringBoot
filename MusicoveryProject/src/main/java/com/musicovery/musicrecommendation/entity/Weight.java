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
@IdClass(Weight.WeightPK.class)
public class Weight {

    @Id
    @Column(nullable = false)
    private String userId;  // 사용자 ID

    @Id
    @Column(nullable = false)
    private String musicId;  // 음악 ID

    @Column(nullable = false)
    private Integer weightAmount;  // 가중치

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeightPK implements Serializable {
        /**
		 * 
		 */
		private static final long serialVersionUID = -1067861242594876139L;
		private String userId;
        private String musicId;
    }
}

