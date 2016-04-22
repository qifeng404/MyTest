package com.test.mytest.service;

import com.test.mytest.model.User;

/**
 * User service.
 */
public interface UserService {

	/**
	 * Gets user info for specified user ID.
	 * 
	 * @param id
	 *            user ID
	 * @return
	 */
	User getUserInfo(long id);

	/**
	 * Updates user info.
	 * 
	 * @param user
	 *            user info
	 * @return -1 means fail, 0 means success.
	 */
	int updateUserInfo(User user);

}
