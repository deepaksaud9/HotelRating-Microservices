package com.user.service.external.services;

import com.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    //get


    //post
    @PostMapping("/rating/save")
    public Rating createRating(Rating values);


    //put


}
