package com.ciber.tweet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ciber.tweet.domain.Tweet;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, Long>{
	Optional<Tweet> findById(Long id);
	List<Tweet> findByIdAndValidate(Long id, boolean validate);
	@Query("{'user.screenName' : ?0,'validate': ?1}")
	List<Tweet> findByUserAndValidate(String user, boolean validate);
}
