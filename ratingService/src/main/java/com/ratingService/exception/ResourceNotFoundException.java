package com.ratingService.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("resource not found in the database");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }

}
