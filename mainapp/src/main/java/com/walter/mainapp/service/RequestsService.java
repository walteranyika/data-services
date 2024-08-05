package com.walter.mainapp.service;

import com.walter.mainapp.dtos.LoanRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RequestsService {
    Page<LoanRequestDto> search(int pageNum, int pageSize, String phone);
}
