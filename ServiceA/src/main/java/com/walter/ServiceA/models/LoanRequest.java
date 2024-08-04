package com.walter.ServiceA.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_requests")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoanRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "amount")
    private double amount;

    @Basic(optional = false)
    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @PrePersist
    public void setCreatedAt(){
        createdAt = LocalDateTime.now();
    }
}
