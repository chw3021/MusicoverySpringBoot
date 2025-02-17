package com.musicovery.customersupport.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.musicovery.customersupport.dto.CustomerSupportDTO;
import com.musicovery.customersupport.entity.CustomerSupport;
import com.musicovery.customersupport.repository.CustomerSupportRepository;

@Service
public class CustomerSupportServiceImpl implements CustomerSupportService {

    private final CustomerSupportRepository customerSupportRepository;

    public CustomerSupportServiceImpl(CustomerSupportRepository customerSupportRepository) {
        this.customerSupportRepository = customerSupportRepository;
    }

    @Override
    public CustomerSupport createInquiry(CustomerSupportDTO customerSupportDTO) {
        CustomerSupport inquiry = CustomerSupport.builder()
                .userId(customerSupportDTO.getUserId())
                .question(customerSupportDTO.getQuestion())
                .createdAt(LocalDateTime.now())
                .build();
        return customerSupportRepository.save(inquiry);
    }

    @Override
    public List<CustomerSupport> getUserInquiries(Long userId) {
        return customerSupportRepository.findByUserId(userId);
    }

    @Override
    public CustomerSupport respondToInquiry(Long inquiryId, String response) {
        Optional<CustomerSupport> inquiryOptional = customerSupportRepository.findById(inquiryId);
        if (inquiryOptional.isPresent()) {
            CustomerSupport inquiry = inquiryOptional.get();
            inquiry.setResponse(response);
            inquiry.setRespondedAt(LocalDateTime.now());
            return customerSupportRepository.save(inquiry);
        }
        throw new RuntimeException("문의 내역을 찾을 수 없습니다.");
    }
}