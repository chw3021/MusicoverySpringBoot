package com.musicovery.admin.entity;

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
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "recommendation")
public class Recommendation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recommendationId; // 추천 알고리즘 ID

	@Column(nullable = false, length = 100)
	private String algorithmName; // 알고리즘 이름

	@Column(name = "weight_amount", nullable = false)
	private double weightAmount; // 추천 가중치

	@Column(nullable = false)
	private boolean isActive; // 활성화 여부

	public void setWeight(double weightAmount) {
		this.weightAmount = weightAmount;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
