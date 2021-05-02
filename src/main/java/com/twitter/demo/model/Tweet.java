package com.twitter.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tweet")
public class Tweet {
	
	private Long id;
	private String name;
	private String text;
	private String location;
	private Boolean validation;

	
		
	public Tweet() {
	}
		
	public Tweet(Long id, String name, String text, String location, Boolean validation) {
		this.id = id;
		this.name = name;
		this.text = text;
		this.location = location;
		this.validation = validation;
	}
	
	@Id
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name", nullable = false)
	public String getUser() {
		return name;
	}
	
	public void setUser(String name) {
		this.name = name;
	}
	
	@Column(name = "text", length = 1024, nullable = false)
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@Column(name = "location", nullable = false)
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(name = "validation", nullable = false)
	public Boolean getValidation() {
		return validation;
	}
	
	public void setValidation(Boolean validation) {
		this.validation = validation;
	}

}
