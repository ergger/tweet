package com.ciber.tweet.service.impl;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ciber.tweet.Lang;
import com.ciber.tweet.domain.Tweet;
import com.ciber.tweet.domain.TweetUser;
import com.ciber.tweet.repository.TweetRepository;
import com.ciber.tweet.service.TweetService;
import com.ciber.tweet.service.oauth2.TweetSet;

@Service
public class TweetServiceImpl implements TweetService {

	private List<String> langs = new ArrayList<>(Arrays.asList(Lang.es.toString(),Lang.fr.toString(),Lang.it.toString()));
	
	@Value("${app.max.follower}")
	private Long maxFollower;

	@Autowired
	private TweetSet tweetSet;

	@Autowired
	private TweetRepository tweetRepository;

	@Override
	public List<Tweet> saveTweetsByUser(String user) throws KeyManagementException, NoSuchAlgorithmException {
		List<Tweet> tweets = new ArrayList<>();
		JSONObject json = new JSONObject(tweetSet.execute(user));
		JSONArray jsonArray = json.getJSONArray("statuses");
		for(Object ob: jsonArray) {
			JSONObject jsonOb = (JSONObject) ob;
			JSONObject jsonUs = jsonOb.getJSONObject("user");
			if(maxFollower < jsonUs.getLong("followers_count")
				&& langs.contains(jsonOb.getString("lang"))) {
				Tweet tweet = Tweet.builder()
						.id(jsonOb.getLong("id"))
						.text(jsonOb.getString("text"))
						.lang(jsonOb.getString("lang"))
						.validate(true)
						.user(TweetUser.builder()
								.id(jsonUs.getLong("id"))
								.followersCount(jsonUs.getLong("followers_count"))
								.name(jsonUs.getString("name"))
								.screenName(jsonUs.getString("screen_name"))
								.location(jsonUs.getString("location"))
								.description(jsonUs.getString("description"))
								.build())
						.build();
				tweetRepository.save(tweet);
				tweets.add(tweet);
			}
		}
		return tweets;
	}

	@Override
	public List<Tweet> getTweets() {
		return tweetRepository.findAll();
	}

	@Override
	public Tweet validateTweet(Long id, boolean validate) {
		Optional<Tweet> optional = tweetRepository.findById(id);
		if(optional.isPresent()) {
			Tweet tweet = optional.get();
			tweet.setValidate(validate);
			return tweetRepository.save(tweet);
		}
		return null;
	}

	@Override
	public List<Tweet> getValidateTweetByUser(String user) {
		return tweetRepository.findByUserAndValidate(user,true);
	}

}
