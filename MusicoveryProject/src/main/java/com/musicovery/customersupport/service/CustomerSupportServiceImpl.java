package com.musicovery.customersupport.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.musicovery.customersupport.entity.CustomerSupport;
import com.musicovery.customersupport.repository.CustomerSupportRepository;

@Service
public class CustomerSupportServiceImpl implements CustomerSupportService {

    private final CustomerSupportRepository customerSupportRepository;

    public CustomerSupportServiceImpl(CustomerSupportRepository customerSupportRepository) {
        this.customerSupportRepository = customerSupportRepository;
    }

    @Override
    public CustomerSupport createInquiry(CustomerSupport inquiry) {
        return customerSupportRepository.save(inquiry);
    }

    @Override
    public List<CustomerSupport> getUserInquiries(String userId) {
        return customerSupportRepository.findByUser_Id(userId);
    }

    @Override
    public Page<CustomerSupport> getInquiries(Integer page, Integer size, Boolean responded) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        if (responded == null) {
            return customerSupportRepository.findAll(pageRequest);
        } else if (responded) {
            return customerSupportRepository.findByRespondedAtIsNotNull(pageRequest);
        } else {
            return customerSupportRepository.findByRespondedAtIsNull(pageRequest);
        }
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