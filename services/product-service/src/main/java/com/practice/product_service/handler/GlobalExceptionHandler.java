package com.practice.product_service.handler;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.practice.product_service.exception.ProductPurchaseException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> productNotFoundExceptionHandler(EntityNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(ProductPurchaseException.class)
  public ResponseEntity<String> productPurchaseResponseExceptionHandler(ProductPurchaseException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
    var errors = new HashMap<String, String>();
    ex.getBindingResult().getFieldErrors().forEach((error) -> {
      var fieldName = ((FieldError) error).getField();
      var fieldErrorMessage = error.getDefaultMessage();
      errors.put(fieldName, fieldErrorMessage);
    });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
  }

}
