package com.example.paymentservice.repository;

import com.example.paymentservice.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Balance findByUserId(Long userId);

    List<Balance> findByUserIdOrderByCreateAtDesc(Long userId);


}
