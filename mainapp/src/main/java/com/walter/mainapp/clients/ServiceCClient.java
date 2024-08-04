package com.walter.mainapp.clients;

import com.walter.mainapp.dtos.LoanRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "ServiceC", url = "http://localhost:9002")
public interface ServiceCClient extends MainClient{

    @GetMapping("/v1/search")
    Page<LoanRequestDto> searchLoanRequests(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam String phone
    );
}
