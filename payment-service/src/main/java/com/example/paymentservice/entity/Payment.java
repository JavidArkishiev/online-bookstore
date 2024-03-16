package com.example.paymentservice.entity;

import com.example.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private Double amount;
    private LocalDateTime createAt = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
