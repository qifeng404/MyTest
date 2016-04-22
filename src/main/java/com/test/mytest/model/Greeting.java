package com.test.mytest.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting {
	
	private long gid;  
    private String content;  
  
    @JsonCreator  
    @JsonIgnoreProperties(ignoreUnknown = true)  
    public Greeting(@JsonProperty(value = "gid") long gid, @JsonProperty(value = "content") String content) {  
        this.gid = gid;  
        this.content = content;  
    }

	public long getGid() {
		return gid;
	}

	public void setGid(long gid) {
		this.gid = gid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Greeting [gid=" + gid + ", content=" + content + "]";
	}  
	
	

}
