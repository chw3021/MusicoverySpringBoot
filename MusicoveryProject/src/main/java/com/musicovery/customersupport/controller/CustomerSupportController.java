package com.musicovery.customersupport.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.customersupport.dto.CustomerSupportDTO;
import com.musicovery.customersupport.entity.CustomerSupport;
import com.musicovery.customersupport.service.CustomerSupportService;

@RestController
@RequestMapping("/api/customersupport")
public class CustomerSupportController {

    private final CustomerSupportService customerSupportService;

    public CustomerSupportController(CustomerSupportService customerSupportService) {
        this.customerSupportService = customerSupportService;
    }

    @PostMapping("/inquiry")
    public CustomerSupport createInquiry(@RequestBody CustomerSupportDTO customerSupportDTO) {
        return customerSupportService.createInquiry(customerSupportDTO);
    }

    @GetMapping("/inquiries/{userId}")
    public List<CustomerSupport> getUserInquiries(@PathVariable Long userId) {
        return customerSupportService.getUserInquiries(userId);
    }

    @PostMapping("/respond/{inquiryId}")
    public CustomerSupport respondToInquiry(@PathVariable Long inquiryId, @RequestBody String response) {
        return customerSupportService.respondToInquiry(inquiryId, response);
    }
}