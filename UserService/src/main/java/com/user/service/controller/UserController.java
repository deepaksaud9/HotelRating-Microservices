package com.user.service.controller;

import com.user.service.entities.User;
import com.user.service.response.ApiResponse;
import com.user.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user){
       User savedUser = userService.saveUser(user);
        ApiResponse<User> response = ApiResponse.<User>builder()
                .success(true)
                .statusName(HttpStatus.CREATED.name())
                .message("User Successfully Created")
                .statusCode(HttpStatus.CREATED.value())
                .response(savedUser)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllUser(){

       List<User> allUser = userService.getAllUser();
       ApiResponse<List<User>> userResponse = ApiResponse.<List<User>>builder()
               .statusName(HttpStatus.OK.name())
               .statusCode(HttpStatus.OK.value())
               .success(true)
               .message("list of user found")
               .response(allUser)
               .build();

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    int retryCount = 1;
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<ApiResponse> getUser(@PathVariable("userId") String userId){

        LOGGER.info("Get Single User Handler : UserController");
        LOGGER.info("RetryCount count :{}",retryCount);
        retryCount++;

        User user = userService.getUserById(userId);
        ApiResponse<User> userResponse = ApiResponse.<User>builder()
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("user Found with userId: "+userId)
                .response(user)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    //fallback method for circuit break

    public ResponseEntity<ApiResponse> ratingHotelFallback(String userId, Exception ex){

//        LOGGER.info("Fallback is Executed because service is down",ex.getMessage());
        ApiResponse<User> userResponse = ApiResponse
                .<User>builder()
                .statusName(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("this ia a fallback method")
                .response(
                         User.builder()
                                 .userId("not found")
                                 .name("not found")
                                 .email("not found")
                                 .about("not found")
                                 .build()
                )
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
