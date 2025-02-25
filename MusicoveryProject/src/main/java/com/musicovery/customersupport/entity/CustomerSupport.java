package com.musicovery.customersupport.entity;
import java.time.LocalDateTime;

import com.musicovery.user.entity.User;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_support")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class CustomerSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    private User user; // 문의한 사용자

    @Column(nullable = false, length = 500)
    private String question; // 문의 내용

    @Column(length = 1000)
    private String response; // 관리자 응답

    @Column(nullable = false)
    private LocalDateTime createdAt; // 문의 작성 시간

    @Column
    private LocalDateTime respondedAt; // 응답 완료 시간
}