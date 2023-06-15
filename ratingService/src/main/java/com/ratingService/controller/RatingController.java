package com.ratingService.controller;

import com.ratingService.entities.Rating;
import com.ratingService.response.ApiResponse;
import com.ratingService.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> create(@RequestBody Rating rating){

        Rating createRating = ratingService.create(rating);

        ApiResponse<Rating> response = ApiResponse.<Rating>builder()
                .success(true)
                .message("Rating Successfully Created")
                .statusCode(HttpStatus.CREATED.value())
                .statusName(HttpStatus.CREATED.name())
                .response(createRating)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getRatingByUserId(@PathVariable("userId") String userId){

        List<Rating> ratings = ratingService.getRatingByUserId(userId);

        ApiResponse<List<Rating>> response = ApiResponse.<List<Rating>>builder()
                .success(true)
                .statusCode(HttpStatus.FOUND.value())
                .statusName(HttpStatus.FOUND.name())
                .message("successfully found")
                .response(ratings)
                .build();

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }


    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<ApiResponse> getRatingByHotelId(@PathVariable("hotelId") String hotelId){

        List<Rating> ratings = ratingService.getRatingByHotelId(hotelId);

        ApiResponse<List<Rating>> response = ApiResponse.<List<Rating>>builder()
                .success(true)
                .statusName(HttpStatus.FOUND.name())
                .statusCode(HttpStatus.FOUND.value())
                .message("successfully found")
                .response(ratings)
                .build();

        return ResponseEntity.status(HttpStatus.FOUND).body(response);

    }



    //=====================================this is optional part ===================================


    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllRating(){

        List<Rating> getAllRating = ratingService.getAllRating();

        ApiResponse<List<Rating>> response = ApiResponse.<List<Rating>>builder()
                .success(true)
                .message("successfully found")
                .statusCode(HttpStatus.FOUND.value())
                .statusName(HttpStatus.FOUND.name())
                .response(getAllRating)
                .build();

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<ApiResponse> getRating(@PathVariable("ratingId") String ratingId){

        Rating getRating = ratingService.getRatingById(ratingId);

        ApiResponse<Rating> response = ApiResponse.<Rating>builder()
                .success(true)
                .message("Successfully found")
                .statusCode(HttpStatus.FOUND.value())
                .statusName(HttpStatus.FOUND.name())
                .response(getRating)
                .build();
        return ResponseEntity.status(HttpStatus.FOUND).body(response);

    }



}
