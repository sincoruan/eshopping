package com.edu.account.repository;

import com.edu.account.domain.Address;
import com.edu.account.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query(value="select * From payment where userid=?1",nativeQuery = true)
    List<Payment> getPaymentsByUserId(Long userId);
}
