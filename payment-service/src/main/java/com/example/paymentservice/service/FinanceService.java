package com.example.paymentservice.service;

import com.example.paymentservice.entity.Balance;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.enums.PaymentStatus;
import com.example.paymentservice.exception.AmountNotBeZeroException;
import com.example.paymentservice.exception.BalanceNotFoundException;
import com.example.paymentservice.exception.PaymentException;
import com.example.paymentservice.model.dto.PaymentDto;
import com.example.paymentservice.repository.BalanceRepository;
import com.example.paymentservice.repository.FinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private final FinanceRepository financeRepository;
    private final BalanceRepository balanceRepository;

    public PaymentDto makePayment(Long userId, PaymentDto paymentDto) throws AmountNotBeZeroException {

        if (paymentDto.getAmount() == 0) {
            throw new AmountNotBeZeroException("amount not be zero");

        }
        List<Balance> lastBalanceList = balanceRepository.findByUserIdOrderByCreateAtDesc(userId);
        if (lastBalanceList.isEmpty()) {
            throw new BalanceNotFoundException("there is not enough money in the balance." +
                    "Please topUp the balance ");
        }
        Balance lastBalance = lastBalanceList.get(0);
        Payment payment = new Payment();
        if (paymentDto.getAmount() > lastBalance.getBalances()) {
            throw new PaymentException("there is not enough money in the balance. " +
                    "Please topUp the balance");
        }

        double newBalance = lastBalance.getBalances() - paymentDto.getAmount();
        payment.setAmount(paymentDto.getAmount());
        payment.setUserId(userId);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        financeRepository.save(payment);

        Balance balance = new Balance();
        balance.setBalances(newBalance);
        balance.setAmount(payment.getAmount());
        balance.setUserId(userId);
        balanceRepository.save(balance);

        PaymentDto paymentDto1 = new PaymentDto();
        paymentDto1.setAmount(payment.getAmount());
        paymentDto.setCreateAt(payment.getCreateAt());
        paymentDto.setPaymentStatus(payment.getPaymentStatus());

        return paymentDto;
    }
}
