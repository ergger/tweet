package com.ciber.tweet.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@Document("user")
public class TweetUser {
	private Long id;
	private Long followersCount;
	private String name;
	private String screenName;
	private String location;
	private String description;
}
