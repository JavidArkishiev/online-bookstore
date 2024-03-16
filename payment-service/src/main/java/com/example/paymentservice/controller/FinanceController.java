package com.example.paymentservice.controller;

import com.example.paymentservice.exception.AmountNotBeZeroException;
import com.example.paymentservice.model.dto.PaymentDto;
import com.example.paymentservice.service.FinanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("finance")
public class FinanceController {
    private final FinanceService financeService;

    @PostMapping("pay")
    public ResponseEntity<PaymentDto> doPayment(@RequestParam Long userId,
                                                @Valid @RequestBody  PaymentDto paymentDto) throws AmountNotBeZeroException {
        return new ResponseEntity<>(financeService.makePayment(userId, paymentDto), HttpStatus.CREATED);

    }

}
