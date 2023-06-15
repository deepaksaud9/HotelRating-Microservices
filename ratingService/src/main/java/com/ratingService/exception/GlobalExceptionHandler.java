package com.ratingService.exception;

import com.ratingService.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFound(ResourceNotFoundException ex){

        String message = ex.getMessage();
        ApiResponse response = ApiResponse
                .builder()
                .message(message)
                .success(true)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .statusName(HttpStatus.NOT_FOUND.name())
                .response(ex)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
