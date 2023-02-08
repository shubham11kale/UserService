package com.microservices.user.service.externals.services;

import com.microservices.user.service.models.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {
    //create
    @PostMapping("/ratings")
    public Rating createRating(Rating rating);

    //put
    @PutMapping("/ratings/{ratingId}")
    public Rating updateRating(@PathVariable String ratingId, Rating rating);

    //Delete Rating
    @DeleteMapping("/ratings/{ratingId}")
    public void deleteRating(@PathVariable String ratingId);
}
