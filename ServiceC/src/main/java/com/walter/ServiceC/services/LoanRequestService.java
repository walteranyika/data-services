package com.walter.ServiceC.services;


import com.walter.ServiceC.dtos.LoanRequestDto;
import com.walter.ServiceC.models.LoanRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanRequestService {
    public void insert(LoanRequest loanRequest);

    public Page<LoanRequestDto> getRequests(int pageNum, int pageSize, String phone);
}