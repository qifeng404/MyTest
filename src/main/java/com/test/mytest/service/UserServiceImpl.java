package com.test.mytest.service;

import org.springframework.stereotype.Service;

import com.test.mytest.model.User;

@Service("userService")
public class UserServiceImpl  implements UserService {

	@Override
	public User getUserInfo(long id) {
		// TODO Auto-generated method stub
		User user = new User(id, "qifeng");
		return user;
	}

	@Override
	public int updateUserInfo(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

}
