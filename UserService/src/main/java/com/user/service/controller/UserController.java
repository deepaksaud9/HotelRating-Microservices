package com.user.service.controller;

import com.user.service.entities.User;
import com.user.service.response.ApiResponse;
import com.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

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
               .statusName(HttpStatus.FOUND.name())
               .statusCode(HttpStatus.FOUND.value())
               .success(true)
               .message("list of user found")
               .response(allUser)
               .build();

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable("userId") String userId){

        User user = userService.getUserById(userId);
        ApiResponse<User> userResponse = ApiResponse.<User>builder()
                .statusName(HttpStatus.FOUND.name())
                .statusCode(HttpStatus.FOUND.value())
                .message("user Found with "+userId)
                .response(user)
                .build();

        return ResponseEntity.status(HttpStatus.FOUND).body(userResponse);
    }
}
