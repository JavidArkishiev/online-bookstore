package com.example.paymentservice.service;

import com.example.paymentservice.entity.Balance;
import com.example.paymentservice.exception.AmountNotBeZeroException;
import com.example.paymentservice.exception.BalanceNotFoundException;
import com.example.paymentservice.model.BalanceAmount;
import com.example.paymentservice.model.dto.BalanceDto;
import com.example.paymentservice.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;

    public void topUp(Long userId, BalanceAmount balanceAmount) throws AmountNotBeZeroException {
        if (balanceAmount.getAmount()==0){
            throw new AmountNotBeZeroException("amount not be zero");

        }

        List<Balance> balanceList = balanceRepository.findByUserIdOrderByCreateAtDesc(userId);

        if (!balanceList.isEmpty()) {
            Balance lastBalance = balanceList.get(0);
            double newBalance = lastBalance.getBalances() + balanceAmount.getAmount();

            Balance balanceEntity = new Balance();
            balanceEntity.setAmount(balanceAmount.getAmount());
            balanceEntity.setBalances(newBalance);
            balanceEntity.setUserId(userId);
            balanceEntity.setCreateAt(LocalDateTime.now());
            balanceRepository.save(balanceEntity);
        } else {

            Balance balanceEntity = new Balance();
            balanceEntity.setAmount(balanceAmount.getAmount());
            balanceEntity.setBalances(balanceAmount.getAmount());
            balanceEntity.setUserId(userId);
            balanceRepository.save(balanceEntity);
        }
    }


    public BalanceDto getBalanceByUserId(Long userId) {
        List<Balance> userBalances = balanceRepository.findByUserIdOrderByCreateAtDesc(userId);

        if (!userBalances.isEmpty()) {
            Balance latestBalance = userBalances.get(0);

            BalanceDto balanceDto = new BalanceDto();
            balanceDto.setBalance(latestBalance.getBalances());
            balanceDto.setCreateAt(latestBalance.getCreateAt());

            return balanceDto;
        } else {
            throw new BalanceNotFoundException("balance not found");
        }

    }

    public BalanceDto getBalanceByBalanceId(Long id) {
        Balance balance = balanceRepository.findById(id).orElseThrow(() -> new BalanceNotFoundException("balance not found"));
        BalanceDto balanceDto = new BalanceDto();
        balanceDto.setBalance(balance.getBalances());
        balanceDto.setCreateAt(balance.getCreateAt());
        return balanceDto;
    }

    public List<BalanceDto> getAllBalance() {
        List<Balance> balanceList = balanceRepository.findAll();
        if (balanceList.isEmpty()) {
            throw new BalanceNotFoundException("balance not found");
        }
        return balanceList.stream().map(balance -> {
            BalanceDto balanceDto = new BalanceDto();
            balanceDto.setBalance(balance.getBalances());
            balanceDto.setCreateAt(balance.getCreateAt());
            return balanceDto;
        }).collect(Collectors.toList());
    }
}
