package com.walter.ServiceB.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan_requests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LoanRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "amount")
    double amount;

    @Basic(optional = false)
    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdAt;

    @PrePersist
    public void setCreatedAt(){
        createdAt = LocalDateTime.now();
    }
}
