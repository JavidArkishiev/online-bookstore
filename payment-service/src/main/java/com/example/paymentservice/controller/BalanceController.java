package com.example.paymentservice.controller;

import com.example.paymentservice.exception.AmountNotBeZeroException;
import com.example.paymentservice.model.BalanceAmount;
import com.example.paymentservice.model.dto.BalanceDto;
import com.example.paymentservice.service.BalanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("balance")
public class BalanceController {
    private final BalanceService balanceService;

    @PostMapping("addBalance")
    public ResponseEntity<String> topUp(@RequestParam Long userId,
                                        @Valid @RequestBody BalanceAmount balanceAmount) throws AmountNotBeZeroException {
        balanceService.topUp(userId, balanceAmount);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("getBalance/ByUserId")
    public ResponseEntity<BalanceDto> getBalance(@RequestParam Long userId) {
        return new ResponseEntity<>(balanceService.getBalanceByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("getBalance/ByBalanceId")
    public ResponseEntity<BalanceDto> getBalanceById(@RequestParam Long id) {
        return new ResponseEntity<>(balanceService.getBalanceByBalanceId(id), HttpStatus.OK);
    }

    @GetMapping("getAllBalance")
    public ResponseEntity<List<BalanceDto>> getAllBalance() {
        return new ResponseEntity<>(balanceService.getAllBalance(), HttpStatus.OK);
    }
}
