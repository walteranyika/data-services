package com.walter.ServiceB.services;

import com.walter.ServiceB.dtos.LoanRequestDto;
import com.walter.ServiceB.models.LoanRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanRequestService {
    public void insert(LoanRequest loanRequest);

    public Page<LoanRequestDto>  getRequests(int pageNum, int pageSize, String phone);
}