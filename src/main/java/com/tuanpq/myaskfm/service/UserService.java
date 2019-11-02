/**
 * @author admin
 * @date 26-10-2019
 */

package com.tuanpq.myaskfm.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tuanpq.myaskfm.entity.User;
import com.tuanpq.myaskfm.model.TempUser;

public interface UserService extends UserDetailsService {

	public User findByUsername(String username);

	public void updateUserInfo(String username, User user);

	public void save(User user);

	public void registerNewAccount(TempUser tempUser);

	public boolean isUsernameExisted(String username);

	public boolean isPasswordCorrect(String username, String password);

	public void updateUserPassword(String username, String password);
	
}
