package com.ratingService.service;

import com.ratingService.entities.Rating;

import java.util.List;

public interface RatingService {

    Rating create(Rating rating);

    List<Rating> getRatingByUserId(String userId);
    List<Rating> getRatingByHotelId(String hotelId);








    List<Rating> getAllRating();

    Rating getRatingById(String ratingId);


}
