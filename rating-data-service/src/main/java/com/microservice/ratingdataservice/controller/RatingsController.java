package com.microservice.ratingdataservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.ratingdataservice.models.Rating;
import com.microservice.ratingdataservice.models.UserRating;

@RestController
@RequestMapping("/ratings")
public class RatingsController {
	
	
	private HashMap<String, Integer> hm= new HashMap<String,Integer>();
	
	@Bean
	public void populate() {
		hm.put("1234",4);
		hm.put("7890",5);
		hm.put("7822",3);
	}
	
	@GetMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId,hm.get(movieId));
	}
	
	@GetMapping("users/{userId}")
	public UserRating getRatingsForUser(@PathVariable("userId") String userId){
		List<Rating> ratings= new ArrayList<Rating>();
		ratings.add(new Rating("1234", 4));
		ratings.add(new Rating("4567", 5));
		ratings.add(new Rating("8910", 3));
		
		UserRating userRating = new UserRating();
		userRating.setUserRatings(ratings);
		return userRating;
	}

}
