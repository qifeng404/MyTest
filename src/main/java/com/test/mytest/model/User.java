package com.test.mytest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	
	private long id;  
    private String name;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setUserName(String name) {
		this.name = name;
	}
	
	@JsonCreator  
    @JsonIgnoreProperties(ignoreUnknown = true)  
    public User(@JsonProperty(value = "id") long id, @JsonProperty(value = "name") String name) {  
        this.id = id;  
        this.name = name;  
    }
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	} 
    
    

}
