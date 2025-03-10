package com.musicovery.customersupport.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.musicovery.customersupport.entity.CustomerSupport;

public interface CustomerSupportService {
	CustomerSupport respondToInquiry(Long inquiryId, String response);

	List<CustomerSupport> getUserInquiries(String email);

	CustomerSupport createInquiry(CustomerSupport inquiry);

	Page<CustomerSupport> getInquiries(Integer page, Integer size, Boolean responded);

	int countTotalInquiries();
}