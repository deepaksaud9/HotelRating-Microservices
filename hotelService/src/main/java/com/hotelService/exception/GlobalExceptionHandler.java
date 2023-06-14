package com.hotelService.exception;

import com.hotelService.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> apiHandlerResourceNotFoundException(ResourceNotFoundException ex){

        String message = ex.getMessage();
        ApiResponse builder = ApiResponse.builder()
                .message(message)
                .success(true)
                .statusName(HttpStatus.NOT_FOUND.name())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .success(true)
                .response(ex)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(builder);

    }
}
