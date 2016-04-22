package com.test.mytest.model.powermock;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
	private long id;  
    private String name;  
  
    @JsonCreator  
    @JsonIgnoreProperties(ignoreUnknown = true)  
    public Employee(@JsonProperty(value = "id") long id, @JsonProperty(value = "name") String name) {  
        this.id = id;  
        this.name = name;  
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + "]";
	}
    
    
}
