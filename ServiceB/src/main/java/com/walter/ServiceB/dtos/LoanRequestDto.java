package com.walter.ServiceB.dtos;

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
    public long id;

    public String name;

    public String email;

    public String phone;

    public double amount;

    public LocalDateTime createdAt;

    public String source;
}
