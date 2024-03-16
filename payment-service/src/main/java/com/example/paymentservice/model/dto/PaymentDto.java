package com.example.paymentservice.model.dto;

import com.example.paymentservice.enums.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    @PositiveOrZero(message = "amount must be a non-negative value")
    @NotNull(message = "amount not be null")
    public Double amount;
    private LocalDateTime createAt;
    private PaymentStatus paymentStatus;
}
