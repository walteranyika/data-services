package com.walter.ServiceC.controller;


import com.walter.ServiceC.dtos.LoanRequestDto;
import com.walter.ServiceC.services.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RequestsController {

    private final LoanRequestService loanRequestService;

    @Autowired
    public RequestsController(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }

    @GetMapping("/search")
    public Page<LoanRequestDto> searchRequests(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam String phone
    ) {
        return loanRequestService.getRequests(pageNum, pageSize, phone);
    }
}
