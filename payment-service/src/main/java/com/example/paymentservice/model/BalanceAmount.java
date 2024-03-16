package com.example.paymentservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceAmount {
    @PositiveOrZero(message = "amount must be a non-negative value")
    @NotNull(message = "amount not be null")
    public Double amount;
}
