package com.user.service.exceptions;

import com.user.service.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex){
//        String message = ex.getMessage();
       ApiResponse response = ApiResponse
               .builder()
               .message("not found")
               .success(true)
               .statusCode(HttpStatus.NOT_FOUND.value())
               .build();

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }
}
