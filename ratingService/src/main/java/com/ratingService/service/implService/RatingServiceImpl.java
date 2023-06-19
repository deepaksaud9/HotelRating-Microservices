package com.ratingService.service.implService;

import com.ratingService.entities.Rating;
import com.ratingService.exception.ResourceNotFoundException;
import com.ratingService.repository.RatingRepository;
import com.ratingService.response.ApiResponse;
import com.ratingService.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepo;



    @Override
    public Rating create(Rating rating) {

        Rating saveRating = ratingRepo.save(rating);

        return saveRating;
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {

        List<Rating> optionalRatings = ratingRepo.findRatingByUserId(userId);
//        if (optionalRatings.isEmpty()){
//            throw new ResourceNotFoundException("no Data is available");
//        }

        if(optionalRatings.isEmpty()){
            ApiResponse.builder()
                    .message("no rating available")
                    .statusCode(HttpStatus.FOUND.value())
                    .statusName(HttpStatus.FOUND.name())
                    .success(true)
                    .response(optionalRatings.isEmpty())
                    .build();

        }

        return optionalRatings;
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {

        List<Rating> optionalRatings = ratingRepo.findRatingByHotelId(hotelId);

        if(optionalRatings.isEmpty()){
            ApiResponse.builder()
                    .message("no rating available")
                    .statusCode(HttpStatus.FOUND.value())
                    .statusName(HttpStatus.FOUND.name())
                    .success(true)
                    .response(optionalRatings.isEmpty())
                    .build();

        }
        return optionalRatings;
    }






    //========================== get all rating =============
    @Override
    public List<Rating> getAllRating() {

        List<Rating> getAllRating = ratingRepo.findAll();
        return getAllRating;
    }

    @Override
    public Rating getRatingById(String ratingId) {

        Optional<Rating> getRating = ratingRepo.findById(ratingId);
        if (!getRating.isPresent()){
            throw new ResourceNotFoundException("Rating Not Found In database !!");
        }

        return getRating.get();
    }
}
