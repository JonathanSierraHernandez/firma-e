package com.twitter.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "hashtag")
public class Hashtag {
	
	private Long id;
	private String hashtag;
	private Tweet tweet;		
		
	public Hashtag() {
	}
	
	public Hashtag(Long id, String hashtag, Tweet tweet) {
		this.id = id;
		this.hashtag = hashtag;
		this.tweet = tweet;
	}



	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "hashtag", nullable = false)
	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	@ManyToOne
	@JoinColumn(name="tweet")
	public Tweet getTweet() {
		return tweet;
	}
	
	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}
	

}
