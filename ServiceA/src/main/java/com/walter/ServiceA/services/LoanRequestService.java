package com.walter.ServiceA.services;

import com.walter.ServiceA.dtos.LoanRequestDto;
import com.walter.ServiceA.models.LoanRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanRequestService {
    public void insert(LoanRequest loanRequest);

    public Page<LoanRequestDto> getRequests(int pageNum, int pageSize, String phone);
}