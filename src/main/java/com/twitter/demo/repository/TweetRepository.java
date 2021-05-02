package com.twitter.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twitter.demo.model.Tweet;



@Repository
@Transactional
public interface TweetRepository extends JpaRepository<Tweet, Long>{
	
	@Modifying
	@Query(value="update tweet t set t.validation = ?1 where t.id = ?2", nativeQuery = true)
	void setValidationTweet(Boolean True, Long tweetId);
	
	@Query(value = "SELECT * FROM tweet WHERE validation = ?1 and name = ?2", nativeQuery = true)
    List<Tweet> findAllTweetsValidatedByUser(Boolean True, String name);


}
