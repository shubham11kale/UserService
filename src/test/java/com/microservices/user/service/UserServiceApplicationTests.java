package com.microservices.user.service;

import com.microservices.user.service.externals.services.RatingService;
import com.microservices.user.service.models.Rating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private RatingService ratingService;

	@Test
	void createRating(){
		Rating rating = Rating.builder().rating("9").userId("fff").hotelId("ddwws").feedback("This is called by feignClient").build();
		ratingService.createRating(rating);
		System.out.println("New Rating is created");
	}

	//Below delete method is not working
//	@Test
//	void deleteRatingById(String ratingId){
//		ratingId = "63d8efde54e9fc2931540cea";
//		ratingService.deleteRating(ratingId);
//	}

}
