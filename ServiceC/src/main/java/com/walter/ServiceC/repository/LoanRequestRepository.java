package com.walter.ServiceC.repository;

import com.walter.ServiceC.models.LoanRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
    Page<LoanRequest> findAllByPhone(Pageable pageable, String phone);

    long countByPhone(String phone);
}
