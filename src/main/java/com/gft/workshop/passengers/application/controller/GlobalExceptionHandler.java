package com.gft.workshop.passengers.application.controller;

import com.gft.workshop.passengers.domain.exceptions.UserNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", "User not found");
    response.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }
}
