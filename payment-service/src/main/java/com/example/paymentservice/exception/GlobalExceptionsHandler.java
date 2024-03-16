package com.example.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler(BalanceNotFoundException.class)
    public ResponseEntity<ErrorDetails> BalanceNotFoundException(BalanceNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorDetails> PaymentExceptionException(PaymentException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> ValidationException(MethodArgumentNotValidException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage( ex.getBindingResult().getFieldError().getDefaultMessage());
        errorDetails.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AmountNotBeZeroException.class)
    public ResponseEntity<ErrorDetails> AmountNotBeZeroException(AmountNotBeZeroException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage( ex.getMessage());
        errorDetails.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


}
