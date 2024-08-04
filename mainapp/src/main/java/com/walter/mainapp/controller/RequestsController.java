package com.walter.mainapp.controller;

import com.walter.mainapp.dtos.LoanRequestDto;
import com.walter.mainapp.service.RequestsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/call-center")
public class RequestsController {
    private RequestsService requestsService;

    public RequestsController(RequestsService requestsService) {
        this.requestsService = requestsService;
    }

    @GetMapping("/search")
    public List<LoanRequestDto> search(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam String phone
    ){

      return requestsService.search(pageNum, pageSize, phone);
    }
}
