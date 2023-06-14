package com.hotelService.service;

import com.hotelService.entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel createHotel(Hotel hotel);

    List<Hotel> getAllHotel();

    Hotel getHotelById(String hotelId);
}
