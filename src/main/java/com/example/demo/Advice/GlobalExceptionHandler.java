package com.example.demo.Advice;

import com.example.demo.Exception.ResourceNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFound e) {
        ApiError apiError = ApiError.builder()
                .message(e.getMessage())
                .status(404)
                .build();
        return ResponseEntity.status(404).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
       List<String> errorMessages = e.getBindingResult()
                .getAllErrors()
                .stream()
               .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        ApiError apiError = ApiError.builder()
                .message(errorMessages.toString())
                .status(400)
                .build();
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e) {
        ApiError apiError = ApiError.builder()
                .message(e.getMessage())
                .status(500)
                .build();
        return ResponseEntity.status(500).body(apiError);
    }
}
