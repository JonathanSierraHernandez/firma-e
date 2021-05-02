package com.twitter.demo.controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twitter.demo.model.Tweet;
import com.twitter.demo.service.TwitterService;

import twitter4j.TwitterException;

@RestController
public class TwitterController {
	
	@Autowired
	TwitterService twitterService;
	
	/**
	 * Get all tweets which are stored in memory
	 * @return List<Tweet>
	 */
	@GetMapping("/tweets")
	public List<Tweet> allTweets() {
	  return twitterService.getTweetsFromLocalDataBase();
	}

	/**
	 * Validate specific tweet: validate attribute is marked as true
	 * @param tweetId
	 */
	@PutMapping("/tweets/validate/{tweetId}")
	public void validationUser(@PathVariable Long tweetId){
		twitterService.validateTweet(tweetId);
	}
	
	/**
	 * Get all validated tweets of specific user
	 * @param user
	 * @return List<Tweet>
	 */
	@GetMapping("/tweets/validated/{name}")
	public List<Tweet> allTweetsValidated(@PathVariable String name) {
		return twitterService.getValidatedTweets(name);
	}
	
	/**
	 * Get 10 hashtags more used on tweets that are stored in memory
	 * @return 
	 * @throws TwitterException
	 * @throws JsonProcessingException
	 */
	@GetMapping("/tweets/hashtags")
	public Map<String, String> moreUsedHashtags() {
        return twitterService.getHashtags();
	}

	
}
