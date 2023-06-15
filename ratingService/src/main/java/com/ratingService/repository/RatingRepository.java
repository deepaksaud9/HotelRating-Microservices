package com.ratingService.repository;

import com.ratingService.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {

    //custom finder method


    List<Rating> findRatingByUserId(String userId);
    List<Rating> findRatingByHotelId(String hotelId);
}
