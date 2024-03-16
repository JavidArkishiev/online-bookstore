package com.example.paymentservice.exception;

public class BalanceNotFoundException extends RuntimeException {
    public BalanceNotFoundException(String s) {
        super(s);
    }
}
