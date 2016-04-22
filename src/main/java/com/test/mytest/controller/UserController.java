package com.test.mytest.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.mytest.base.UserIDRequest;
import com.test.mytest.base.UserInfoRequest;
import com.test.mytest.base.UserNameResponse;
import com.test.mytest.base.UserResultResponse;
import com.test.mytest.service.UserQueryServiceImpl;

/***
 * User Controller.**@author Bert Lee
 * 
 * @version 2014-8-19
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserQueryServiceImpl userQueryService;

	@RequestMapping(value = "/get_user_name", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserNameResponse getUserName(@Valid UserIDRequest userIDRequest) {
		long userId = userIDRequest.getId();
		if(userQueryService == null){
			userQueryService = new UserQueryServiceImpl();
		}
		String userName = this.userQueryService.getUserName(userId);
		UserNameResponse response = new UserNameResponse();
		if (!StringUtils.isEmpty(userName)) {
			response.setName(userName);
		}

		return response;
	}

	@RequestMapping(value = "/update_user_name", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserResultResponse updateUserName(@Valid @RequestBody UserInfoRequest userInfoRequest) { 
		// JSON request body map
		UserResultResponse response = new UserResultResponse();
		long userId = userInfoRequest.getId();
		String userName = userInfoRequest.getUserName();
		if(userQueryService == null){
			userQueryService = new UserQueryServiceImpl();
		}
		int result = this.userQueryService.updateUserName(userId, userName);
		if (result < 0) {
			response.setResult(result);
			response.setResultMessage("update operation is fail");
		}

		return response;
	}

}
