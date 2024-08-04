package com.walter.mainapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestDto {
    private long id;

    private String name;

    private String email;

    private String phone;

    private double amount;

    private LocalDateTime createdAt;

    public String source;
}
