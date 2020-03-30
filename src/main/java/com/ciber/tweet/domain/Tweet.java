package com.ciber.tweet.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@Document("Tweet")
public class Tweet {
	private Long id;
	private String createdAt;
	private TweetUser user;
	private String lang;
	private String text;
	private boolean validate;
}
