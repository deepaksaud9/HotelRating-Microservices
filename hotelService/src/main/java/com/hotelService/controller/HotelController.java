package com.hotelService.controller;

import com.hotelService.entities.Hotel;
import com.hotelService.response.ApiResponse;
import com.hotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
                .statusCode(HttpStatus.OK.value())
                .statusName(HttpStatus.OK.name())
                .message("Hotel Found Successfully")
                .response(getHotels)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseHotel);


    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<ApiResponse> getHotel(@PathVariable("hotelId") String hotelId){

        Hotel getHotel = hotelService.getHotelById(hotelId);

        ApiResponse<Hotel> hotelApiResponse = ApiResponse
                .<Hotel>builder()
                .success(true)
                .message("Hotel Successfully Found")
                .statusCode(HttpStatus.OK.value())
                .statusName(HttpStatus.OK.name())
                .response(getHotel)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(hotelApiResponse);
    }


}
