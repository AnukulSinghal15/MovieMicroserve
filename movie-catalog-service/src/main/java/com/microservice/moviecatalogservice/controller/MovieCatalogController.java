package com.microservice.moviecatalogservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservice.moviecatalogservice.models.CatalogItem;
import com.microservice.moviecatalogservice.models.Movie;
import com.microservice.moviecatalogservice.models.Rating;
import com.microservice.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
	
	@Autowired  //Somebody has a bean of this type, get me the instance of that bean.Autowired is a consumer, Bean is a producer.
	private RestTemplate restTemplate;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
//		List<String> movieIdList= new ArrayList<String>();
//		movieIdList.add("1234");
//		movieIdList.add("7890");
		
//		for(String movieId: movieIdList) {
//		Movie movie= restTemplate.getForObject("http://localhost:9293/movies/"+movieId, Movie.class);
//		Rating rating= restTemplate.getForObject("http://localhost:9294/ratings/"+movieId, Rating.class);
//		finalList.add(new CatalogItem(movie.getName(),movie.getDesc(),rating.getRating()));
//	}
		
		
		List<CatalogItem> finalList= new ArrayList<CatalogItem>();
		
		//Without eureka, calling the service directly with absolute url
		//UserRating ratings= restTemplate.getForObject("http://localhost:9294/ratings/users/"+userId, UserRating.class);
		
		//With Eureka
		//We are not providng url, but the name in the eureka directory that we passed in application.properties file
		UserRating ratings= restTemplate.getForObject("http://RATING-DATA-SERVICE/ratings/users/"+userId, UserRating.class);
		
		for(Rating rating: ratings.getUserRatings()) {
			//Without eureka, calling the service directly with absolute url
			//Movie movie= restTemplate.getForObject("http://localhost:9293/movies/"+rating.getMovieId(), Movie.class);
			
			//With Eureka     //remember to add @Loadbalanced in the resttemplate bean.
			//We are not providng url, but the name in the eureka directory that we passed in application.properties file
			Movie movie= restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/"+rating.getMovieId(), Movie.class);
			
			finalList.add(new CatalogItem(movie.getName(),movie.getDesc(),rating.getRating()));
		}
		
		return finalList;
		
	}

}
