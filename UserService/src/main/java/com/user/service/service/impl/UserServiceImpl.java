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
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {


//    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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

    // ---------------------------get all user =================

    @Override
    public List<User> getAllUser()  {

        List<User> users = userRepo.findAll();
        if (users.isEmpty()){
            throw new ResourceNotFoundException("no any users in the database");
        }

        System.out.println("The first user is:" +users.get(0).getEmail());
        users.forEach(user ->  {
            Object res = restTemplate.getForObject("http://RATING-SERVICE/rating/user/"+user.getUserId(),Object.class);
            ApiResponse response;
            if(res!=null){
                response = mapper.convertValue(res, ApiResponse.class);

                System.out.println("User: "+user.getEmail()+"The rating is Present .............................");
                if(user.getRatings().isEmpty()){
                    ApiResponse.builder()
                            .statusName(HttpStatus.FOUND.name())
                            .statusCode(HttpStatus.FOUND.value())
                            .message("no rating is available")
                            .success(true)
                            .response(null)
                            .build();
                }
                ArrayList list = mapper.convertValue(response.getResponse(),ArrayList.class);
                ArrayList<ActualRatingResponse> ratings = new ArrayList<>();



                list.stream().forEach(rating -> {
                    Rating rating1 = mapper.convertValue(rating, Rating.class);
                        Object response1 = restTemplate.getForObject("http://HOTEL-SERVICE/hotel/"+rating1.getHotelId(),Object.class);
                    ApiResponse apiResponse = mapper.convertValue(response1,ApiResponse.class);
                    ActualRatingResponse actualRatingResponse = new ActualRatingResponse();
                    actualRatingResponse.setFeedback(rating1.getFeedback());
                    actualRatingResponse.setRatingId(rating1.getRatingId());
                    actualRatingResponse.setHotel(mapper.convertValue(apiResponse.getResponse(), Hotel.class));
                    actualRatingResponse.setUserId(rating1.getUserId());
                    actualRatingResponse.setRating(rating1.getRating());
                    ratings.add(actualRatingResponse);
                    System.out.println("The hotel response is:" + apiResponse.getResponse().toString());
                });

//                System.out.println("The first rating is: "+ratings.get(0).toString());
                logger.info(String.valueOf(ratings));
                user.setRatings(ratings);
            }
            else{

                System.out.println("User: "+user.getEmail()+"The rating is Empty..................................");
            }


        });
        return users;
    }
// ========================get user by id ========================
    @Override
    public User getUserById(String userId) {
        //we are getting from database
        User user = userRepo.findById(userId).get();
        if(user==null){
            throw new ResourceNotFoundException("this does not match to the any user in database");
        }
        //fetching user rating from RATING SERVICE;
        ApiResponse<?> response =  restTemplate.getForObject("http://RATING-SERVICE/rating/user/"+user.getUserId(), ApiResponse.class);
        ArrayList list = mapper.convertValue(response.getResponse(), ArrayList.class);
        ArrayList<ActualRatingResponse> ratings = new ArrayList<>();
        list.stream().forEach(rating->{
            Rating rating1 = mapper.convertValue(rating, Rating.class);
            Object  response1 = restTemplate.getForObject("http://HOTEL-SERVICE/hotel/"+ rating1.getHotelId(),Object.class);
            ApiResponse apiResponse = mapper.convertValue(response1, ApiResponse.class);
            ActualRatingResponse actualRatingResponse = new ActualRatingResponse();
            actualRatingResponse.setFeedback(rating1.getFeedback());
            actualRatingResponse.setRatingId(rating1.getRatingId());
            actualRatingResponse.setHotel(mapper.convertValue(apiResponse.getResponse(), Hotel.class));
            actualRatingResponse.setUserId(rating1.getUserId());
            actualRatingResponse.setRating(rating1.getRating());
            ratings.add(actualRatingResponse);
        });
        user.setRatings(ratings);
         return user;
    }
}
