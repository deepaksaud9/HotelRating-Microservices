package com.user.service.service.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.service.entities.ActualRatingResponse;
import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exceptions.ResourceNotFoundException;
import com.user.service.repository.UserRepository;
import com.user.service.response.ApiResponse;
import com.user.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Consumer;

@Service
public class UserServiceImpl implements UserService {

    ObjectMapper mapper = new ObjectMapper();

   private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserRepository userRepo;
    @Override
    public User saveUser(User user) {
        User savedUser =userRepo.save(user);
        return savedUser;
    }

    @Override
    public List<User> getAllUser() {

        List<User> users = userRepo.findAll();
        if (users.isEmpty()){
            throw new ResourceNotFoundException("no any users in the database");
        }

        return users;
    }

    @Override
    public User getUserById(String userId) {

        //we getting from database
        User user = userRepo.findById(userId).get();
        if(user==null){
            throw new RuntimeException("this does not match to the any user in database");
        }

        //fetching user rating from RATING SERVICE;
        ApiResponse<?> response =  restTemplate.getForObject("http://localhost:8083/rating/user/"+user.getUserId(), ApiResponse.class);


        ArrayList list = mapper.convertValue(response.getResponse(), ArrayList.class);
        ArrayList<ActualRatingResponse> ratings = new ArrayList<>();


        list.stream().forEach(rating->{
            Rating rating1 = mapper.convertValue(rating, Rating.class);
            Object  response1 = restTemplate.getForObject("http://localhost:8082/hotel/"+ rating1.getHotelId(),Object.class);
            ApiResponse apiResponse = mapper.convertValue(response1, ApiResponse.class);
            ActualRatingResponse actualRatingResponse = new ActualRatingResponse();
            actualRatingResponse.setFeedback(rating1.getFeedback());
            actualRatingResponse.setRatingId(rating1.getRatingId());
            actualRatingResponse.setUserId(rating1.getUserId());
            actualRatingResponse.setHotel(mapper.convertValue(apiResponse.getResponse(), Hotel.class));
            actualRatingResponse.setRating(rating1.getRating());
            ratings.add(actualRatingResponse);
        });


        user.setRatings(ratings);

        System.out.println(response.toString());

        logger.trace("{ #####LOGGER-TEST#####}",response);
         return user;
    }
}
