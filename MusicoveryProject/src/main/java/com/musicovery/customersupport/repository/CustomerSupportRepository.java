package com.musicovery.customersupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.customersupport.entity.CustomerSupport;

public interface CustomerSupportRepository extends JpaRepository<CustomerSupport, Long> {
    List<CustomerSupport> findByUserId(Long userId);
}