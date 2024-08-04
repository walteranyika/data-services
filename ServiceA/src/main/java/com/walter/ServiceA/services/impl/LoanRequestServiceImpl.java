package com.walter.ServiceA.services.impl;

import com.walter.ServiceA.dtos.LoanRequestDto;
import com.walter.ServiceA.models.LoanRequest;
import com.walter.ServiceA.repository.LoanRequestRepository;
import com.walter.ServiceA.services.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {
    @Autowired
    private LoanRequestRepository loanRequestRepository;

    @Override
    public void insert(LoanRequest loanRequest) {
       loanRequestRepository.save(loanRequest);
    }

    @Override
    public Page<LoanRequestDto> getRequests(int pageNum, int pageSize, String phone) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<LoanRequest> pageRequests = loanRequestRepository.findAllByPhone(pageable, phone);
        long count = loanRequestRepository.countByPhone(phone);
        var requests = pageRequests.getContent().stream().map(it->
                LoanRequestDto.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .email(it.getEmail())
                        .amount(it.getAmount())
                        .createdAt(it.getCreatedAt())
                        .phone(it.getPhone())
                        .source("A")
                        .build()
        ).toList();

        return new PageImpl<LoanRequestDto>(requests, PageRequest.of(pageNum, pageSize), count);
    }
}
