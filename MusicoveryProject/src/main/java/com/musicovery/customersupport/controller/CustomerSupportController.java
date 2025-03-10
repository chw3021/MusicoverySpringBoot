package com.musicovery.customersupport.controller;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicovery.customersupport.dto.CustomerSupportDTO;
import com.musicovery.customersupport.entity.CustomerSupport;
import com.musicovery.customersupport.service.CustomerSupportService;
import com.musicovery.user.entity.User;
import com.musicovery.user.repository.UserRepository;

@RestController
@RequestMapping("/customersupport")
public class CustomerSupportController {

    private final CustomerSupportService customerSupportService;
    private final UserRepository userRepository; // UserRepository 주입

    public CustomerSupportController(CustomerSupportService customerSupportService, UserRepository userRepository) {
        this.customerSupportService = customerSupportService;
        this.userRepository = userRepository;
    }

    @PostMapping("/inquiry")
    public CustomerSupport createInquiry(@RequestBody CustomerSupportDTO customerSupportDTO) {
        // userId를 사용하여 User 객체 조회
        User user = userRepository.findById(customerSupportDTO.getUserId()).orElse(null);
        if (user == null) {
            throw new RuntimeException("해당 ID를 가진 사용자를 찾을 수 없습니다.");
        }

        // CustomerSupportDTO에서 User 객체 설정
        CustomerSupport inquiry = CustomerSupport.builder()
                .user(user)
                .question(customerSupportDTO.getQuestion())
                .createdAt(LocalDateTime.now())
                .build();

        return customerSupportService.createInquiry(inquiry);
    }

    @GetMapping("/inquiries")
    public List<CustomerSupport> getUserInquiries(@RequestParam String userId) {
        return customerSupportService.getUserInquiries(userId);
    }


    //전체 문의사항 가져오기.
    //responded가 참이면 답변완료한것만, 거짓이면 답변 안된것만
    //파라미터를 아예 안줬으면 전체 문의사항
    @GetMapping("/inquiriesAll")
    public Page<CustomerSupport> getInquiries(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) Boolean responded
    ) {
        return customerSupportService.getInquiries(page, size, responded);
    }

    //해당 문의사항의 ID값을 받아와서 답변등록하기
    @PostMapping("/respond/{inquiryId}")
    public CustomerSupport respondToInquiry(@PathVariable Long inquiryId, @RequestBody String response) {
        return customerSupportService.respondToInquiry(inquiryId, response);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalInquiries() {
        return ResponseEntity.ok(customerSupportService.countTotalInquiries());
    }
}