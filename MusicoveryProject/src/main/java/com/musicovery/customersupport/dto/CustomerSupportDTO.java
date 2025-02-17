package com.musicovery.customersupport.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerSupportDTO {
    private Long userId;
    private String question;
    private String response;
    private LocalDateTime createdAt;
    private LocalDateTime respondedAt;
}