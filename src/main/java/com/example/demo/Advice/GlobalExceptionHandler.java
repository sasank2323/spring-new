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


    private ResponseEntity<ApiResponse<?>> buildErrorResponse(ApiError apierror)
    {
        return ResponseEntity
                .status(apierror.getStatus())
                .body(new ApiResponse<>(apierror));
    }


    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFound e) {
        ApiError apiError = ApiError.builder()
                .message(e.getMessage())
                .status(404)
                .build();
        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
       List<String> errorMessages = e.getBindingResult()
                .getAllErrors()
                .stream()
               .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        ApiError apiError = ApiError.builder()
                .message(errorMessages.toString())
                .status(400)
                .build();
        return buildErrorResponse(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        ApiError apiError = ApiError.builder()
                .message(e.getMessage())
                .status(500)
                .build();
        return buildErrorResponse(apiError);
    }
}
