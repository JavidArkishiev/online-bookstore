package com.example.paymentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.function.Predicate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "balance")
@Entity
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Double balances;
    private Double amount;
    private LocalDateTime createAt = LocalDateTime.now();
}
