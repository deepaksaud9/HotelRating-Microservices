package com.user.service.external.services;

import com.user.service.entities.Hotel;
import com.user.service.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.Path;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @GetMapping("/hotel/{hotelId}")
    Object getHotel(@PathVariable("hotelId") String hotelId);
}
