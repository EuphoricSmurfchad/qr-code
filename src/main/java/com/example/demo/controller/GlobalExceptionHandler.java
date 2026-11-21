package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.exception.QrNotFoundException;
import com.example.demo.exception.UserNotFoundException;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex){
        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(QrNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleQrNotFoundException(QrNotFoundException ex){
        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

}
