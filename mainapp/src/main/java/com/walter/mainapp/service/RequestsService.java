package com.walter.mainapp.service;

import com.walter.mainapp.dtos.LoanRequestDto;

import java.util.List;

public interface RequestsService {
    List<LoanRequestDto> search(int pageNum, int pageSize, String phone);
}
