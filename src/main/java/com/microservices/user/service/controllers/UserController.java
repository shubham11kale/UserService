package com.microservices.user.service.controllers;

import com.microservices.user.service.models.User;
import com.microservices.user.service.repositories.UserRepository;
import com.microservices.user.service.services.Impl.UserServiceImpl;
import com.microservices.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    //Create new User
    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

    //Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUsers = userService.getAllUser();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }


    //public int retryCount = 1;
    //Get user by Id. Here we are using circuit breaker because this method is calling rating and hotel service.
    @GetMapping("/{userId}")
    //@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    //@Retry(name="ratingHotelService", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUser(@PathVariable String userId){
        //logger.info("retry count is: "+retryCount++);
        User user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
        logger.info("Fallback is executed because service is down: ", ex.getMessage());

        User user = User.builder()
                .userId("123")
                .email("dummy@dummy.com")
                .about("Dummy data").build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
