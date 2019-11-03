/**
 * @author admin
 * @date 26-10-2019
 */

package com.tuanpq.myaskfm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tuanpq.myaskfm.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);

	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.email=:email, u.location=:location, u.workplace=:workplace WHERE u.username=:username")
	public int updateUserInfo(@Param("email") String email, @Param("location") String location,
			@Param("workplace") String workplace, @Param("username") String username);
	
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.password=:password WHERE u.username=:username")
	public int updateUserPassword(@Param("password") String encodedPassword, 
			@Param("username") String username);
}
