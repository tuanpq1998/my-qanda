/**
 * @author admin
 * @date 26-10-2019
 */

package com.tuanpq.myaskfm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tuanpq.myaskfm.dao.UserRepository;
import com.tuanpq.myaskfm.entity.User;
import com.tuanpq.myaskfm.model.TempUser;
import com.tuanpq.myaskfm.service.UserService;
import com.tuanpq.myaskfm.utility.Constant;
import com.tuanpq.myaskfm.utility.DateTimeHandler;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private List<GrantedAuthority> getGrantedAuthorityList() {
		List<GrantedAuthority> list = new ArrayList<>();
		GrantedAuthority authority = () -> Constant.DEFAULT_ROLE;
		list.add(authority);
		return list;
	};
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = findByUsername(username);
		
		if (user == null) 
			throw new UsernameNotFoundException("Username " + username
				+ "not found!");
		return new org.springframework.security.core.userdetails.User(username, 
				user.getPassword(), getGrantedAuthorityList());
	}
	
	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public void updateUserInfo(String username, User user) {
		User userFromDb = findByUsername(username);
		userFromDb.setEmail(user.getEmail());
		userFromDb.setLocation(user.getLocation());
		userFromDb.setWorkplace(user.getWorkplace());
		
		save(userFromDb);
	}

	@Override
	public void registerNewAccount(TempUser tempUser) {
		User user = new User();
		user.setId(0);
		user.setFullname(tempUser.getFullname());
		user.setUsername(tempUser.getUsername());
		user.setEmail(tempUser.getEmail());
		user.setJoinDate(DateTimeHandler.convertToSaveToSQL(new Date()));
		user.setPassword(passwordEncoder.encode(tempUser.getPassword()));
		save(user);
	}

	@Override
	public boolean isUsernameExisted(String username) {
		return findByUsername(username)!=null;
	}

	@Override
	public boolean isPasswordCorrect(String username, String password) {
		User user = findByUsername(username);
		return passwordEncoder.matches(password, user.getPassword());
	}

	@Override
	public void updateUserPassword(String username, String password) {
		User user = findByUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		save(user);
	}

}
