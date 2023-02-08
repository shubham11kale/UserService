package com.microservices.user.service.services.Impl;

import com.microservices.user.service.exceptions.UserNotFoundException;
import com.microservices.user.service.externals.services.HotelService;
import com.microservices.user.service.models.Hotel;
import com.microservices.user.service.models.Rating;
import com.microservices.user.service.models.User;
import com.microservices.user.service.repositories.UserRepository;
import com.microservices.user.service.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;
    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //Generate unique user id
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User doe snot found with id "+userId));
        //Get ratings of user using user ID
        //http://localhost:8083/ratings/users/3a9b7c6f-8ace-4a7e-be15-947d0661f4f5

        Rating[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+userId, Rating[].class);
        List<Rating> allRatingsOfUser = Arrays.stream(ratings).toList();
        //Now we will pull Hotel associated with each rating using hotelId mentioned in the rating and assign hotel to that rating
        allRatingsOfUser = allRatingsOfUser.stream().map(rating -> {
            //Now using hotelId, pull Hotel(using HotelService) and assign that hotel to rating object
            //http://localhost:8082/hotels/376a6c9a-e869-4ddd-bf27-bc150286821d

            String hotelId = rating.getHotelId();
            //ResponseEntity<Hotel> response = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+hotelId, Hotel.class);
            Hotel hotel= hotelService.getHotel(hotelId);
            //logger.info("response status code: {}", response.getStatusCode());
            //rating.setHotel(response.getBody());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(allRatingsOfUser);
        return user;
    }
}
