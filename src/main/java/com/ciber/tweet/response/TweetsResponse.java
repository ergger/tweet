package com.ciber.tweet.response;

import java.util.List;

import com.ciber.tweet.domain.Tweet;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TweetsResponse {
	private List<Tweet> tweets;
}
