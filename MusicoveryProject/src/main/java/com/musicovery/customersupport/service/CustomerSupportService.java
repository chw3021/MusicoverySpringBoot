package com.musicovery.customersupport.service;

import java.util.List;

import com.musicovery.customersupport.dto.CustomerSupportDTO;
import com.musicovery.customersupport.entity.CustomerSupport;

public interface CustomerSupportService {
    CustomerSupport createInquiry(CustomerSupportDTO customerSupportDTO);
    List<CustomerSupport> getUserInquiries(Long userId);
    CustomerSupport respondToInquiry(Long inquiryId, String response);
}