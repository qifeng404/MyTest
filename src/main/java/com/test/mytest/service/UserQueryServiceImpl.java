package com.test.mytest.service;

import org.springframework.stereotype.Service;

import com.test.mytest.model.User;

/**
 * User query service implementation.
 */
@Service("userQueryService")
public class UserQueryServiceImpl{


  public String getUserName(long userId) {
	User user = new User(userId, "qifeng");
    return user != null ? user.getName() : "";
  }

  public int updateUserName(long userId, String userName) {
    return 0;
  }

}
