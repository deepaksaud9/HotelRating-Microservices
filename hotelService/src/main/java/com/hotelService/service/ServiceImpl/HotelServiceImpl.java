package com.hotelService.service.ServiceImpl;

import com.hotelService.entities.Hotel;
import com.hotelService.exception.ResourceNotFoundException;
import com.hotelService.repository.HotelRepository;
import com.hotelService.response.ApiResponse;
import com.hotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepo;
    @Override
    public Hotel createHotel(Hotel hotel) {

        Hotel saveHotel = hotelRepo.save(hotel);
        return saveHotel;
    }

    @Override
    public List<Hotel> getAllHotel() {

        List<Hotel> hotels = hotelRepo.findAll();

        if(hotels.isEmpty()){
            throw new ResourceNotFoundException("hotel not found");
        }

        return hotels;
    }

    @Override
    public Hotel getHotelById(String hotelId) {


       Optional<Hotel> hotel = hotelRepo.findById(hotelId);

//        if(!hotel.isPresent()){
            if(!hotel.isPresent()){
               throw new ResourceNotFoundException("hotel not found");
            }

        return hotel.get();
    }
}
