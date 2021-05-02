package com.twitter.demo.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.demo.model.Hashtag;
import com.twitter.demo.model.Tweet;
import com.twitter.demo.repository.HashtagRepository;
import com.twitter.demo.repository.TweetRepository;

import twitter4j.*;

@Service
public class TwitterService {
	
	@Autowired
	private TweetRepository tweetRepository;
	@Autowired
	private HashtagRepository hashtagRepository;
    
    public TwitterService() throws TwitterException { 
    	this.getTweetsFromTwitter();
    };
    
    /**
     * Stream mode to get tweets from Twitter and store in database
     */
    public void getTweetsFromTwitter() {
    	
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        StatusListener listener = new StatusListener() {
        	
            @Override
            public void onStatus(Status status) {
            	
                //Store tweet in database if tweet requirements can be met: user, text, location and validation
                if(status.getUser().getName() != null && status.getText() != null && status.getUser().getLocation() != null && (status.getLang().equals("es") || status.getLang().equals("fr") || status.getLang().equals("it")) && status.getUser().getFollowersCount() >= 1500) {
                	Tweet tweet = new Tweet(status.getId(), status.getUser().getName(), status.getText(), status.getUser().getLocation(), false);
                	tweetRepository.save(tweet);
                	
                	//Store hashtags in database: id, hashtag and tweetId
                	if(status.getHashtagEntities() != null) {
                    	for (int i = 0; i < status.getHashtagEntities().length; i++) {     
                    		Hashtag hashtag = new Hashtag();
                    		hashtag.setHashtag(status.getHashtagEntities()[i].getText());
                    		hashtag.setTweet(tweet);
                    		hashtagRepository.save(hashtag);

                    	}
                	}
                }
         
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            	System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            	System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
            	System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
            	System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
       
        twitterStream.addListener(listener);
        twitterStream.sample();
        
    }

    /**
     * Get all tweets sotred in local database
     */
    public List<Tweet> getTweetsFromLocalDataBase() {
    	return tweetRepository.findAll();
    }

    /**
     * Validate a tweet
     */
    public void validateTweet(Long tweetId) {
    	tweetRepository.setValidationTweet(true, tweetId);
    }

    /**
     * Get all validated tweets stored in local database
     */
    public List<Tweet> getValidatedTweets(String name) {
    	return tweetRepository.findAllTweetsValidatedByUser(true, name);
    }
    
    /**
     * Get 10 hashtags more used in tweets that are localy
     */
    public Map<String, String> getHashtags() {
    	//Create map data in order to return Json to client endpoint
    	LinkedHashMap<String, String> hashtags = new LinkedHashMap<>();
    	for (int i = 0; i < hashtagRepository.findHashtagsMoreUsed().length; i++) {     
    		String[] value = hashtagRepository.findHashtagsMoreUsed()[i].split(",",2);
    		hashtags.put(value[0], value[1]);
    	}
    	
    	return hashtags;
    }
    
}
