package com.ciber.tweet.controller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ciber.tweet.domain.Tweet;
import com.ciber.tweet.response.TweetsResponse;
import com.ciber.tweet.service.TweetService;

@RestController
public class TweetController {
	@Autowired
	TweetService tweetService;
	@GetMapping(value = "/tweets/{user}")
	public ResponseEntity<TweetsResponse> saveTweetsByUser(@PathVariable String user)
			throws KeyManagementException, NoSuchAlgorithmException {
		return ResponseEntity.ok().body(TweetsResponse.builder().tweets(tweetService.saveTweetsByUser(user)).build());
	}
	@GetMapping(value = "/tweets")
	public ResponseEntity<TweetsResponse> getTweets (){
		return ResponseEntity.ok().body(TweetsResponse.builder().tweets(tweetService.getTweets()).build());
	}
	@GetMapping(value = "/validate/tweet/{id}/{validate}")
	public ResponseEntity<Tweet> validateTweet(@PathVariable Long id, boolean validate){
		return ResponseEntity.ok().body(tweetService.validateTweet(id,validate));
	}
	@GetMapping(value = "/tweets/user/{user}")
	public ResponseEntity<TweetsResponse> getValidateTweetByUser(@PathVariable String user) {
		return ResponseEntity.ok().body(TweetsResponse.builder().tweets(tweetService.getValidateTweetByUser(user)).build());
	}
}
