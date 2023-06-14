package com.hotelService.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.sql.Struct;


@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("Resource not found in the database");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
