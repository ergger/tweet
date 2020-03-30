package com.ciber.tweet.service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.ciber.tweet.domain.Tweet;

public interface TweetService {

	List<Tweet> saveTweetsByUser(String user) throws KeyManagementException, NoSuchAlgorithmException;

	List<Tweet> getTweets();

	Tweet validateTweet(Long id, boolean validate);

	List<Tweet> getValidateTweetByUser(String user);

}
