package com.hotelService.controller;

import com.hotelService.entities.Hotel;
import com.hotelService.response.ApiResponse;
import com.hotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;


    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Hotel>> createHotel(@RequestBody Hotel hotel){

        Hotel saveHotel = hotelService.createHotel(hotel);

        ApiResponse<Hotel> responseHotel = ApiResponse
                .<Hotel>builder()
                .statusCode(HttpStatus.CREATED.value())
                .statusName(HttpStatus.CREATED.name())
                .message("Hotel Successfully created")
                .response(saveHotel)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseHotel);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllHotels(){

        List<Hotel> getHotels = hotelService.getAllHotel();

        ApiResponse<List<Hotel>> responseHotel = ApiResponse
                .<List<Hotel>>builder()
                .statusCode(HttpStatus.FOUND.value())
                .statusName(HttpStatus.FOUND.name())
                .message("Hotel Found Successfully")
                .response(getHotels)
                .build();

        return ResponseEntity.status(HttpStatus.FOUND).body(responseHotel);


    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<ApiResponse> getHotel(@PathVariable("hotelId") String id){

        Hotel getHotel = hotelService.getHotelById(id);

        ApiResponse<Hotel> hotelApiResponse = ApiResponse
                .<Hotel>builder()
                .success(true)
                .message("Hotel Successfully Found")
                .statusCode(HttpStatus.FOUND.value())
                .statusName(HttpStatus.FOUND.name())
                .response(getHotel)
                .build();

        return ResponseEntity.status(HttpStatus.FOUND).body(hotelApiResponse);
    }


}
