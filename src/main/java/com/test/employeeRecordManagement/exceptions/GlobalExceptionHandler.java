package com.test.employeeRecordManagement.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException businessException){
        return new ResponseEntity<>(new ErrorMessage(businessException.getMessage()),businessException.getHttpStatus());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Set<ErrorMessage>>> handleValidationExceptions(ConstraintViolationException ex) {
        Set<ErrorMessage> errors = new HashSet<>();
        ex.getConstraintViolations().stream().forEach(error ->
                errors.add(new ErrorMessage( error.getMessage()))
        );
        return new ResponseEntity<>(Map.of("errors",errors), HttpStatus.BAD_REQUEST);
    }
}
