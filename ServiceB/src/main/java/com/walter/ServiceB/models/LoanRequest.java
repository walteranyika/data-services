package com.walter.ServiceB.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Random;

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
        Random r = new Random();
        createdAt = LocalDateTime.now().minusDays(r.nextInt() % 10 + 10);
    }
}
