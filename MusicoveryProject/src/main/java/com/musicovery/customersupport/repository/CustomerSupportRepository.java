package com.musicovery.customersupport.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.customersupport.entity.CustomerSupport;


public interface CustomerSupportRepository extends JpaRepository<CustomerSupport, Long> {
    List<CustomerSupport> findByUser_Id(String userId);

	Page<CustomerSupport> findByRespondedAtIsNotNull(PageRequest pageRequest);

	Page<CustomerSupport> findByRespondedAtIsNull(PageRequest pageRequest);
}