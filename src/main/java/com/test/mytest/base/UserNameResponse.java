package com.test.mytest.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User name response info.
 */
public class UserNameResponse {

	@JsonProperty("name")
	protected String name = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserNameResponse [name=" + name + "]";
	}

}
