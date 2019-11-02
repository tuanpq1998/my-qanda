/**
 * @author admin
 * @date 26-10-2019
 */

package com.tuanpq.myaskfm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuanpq.myaskfm.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);

}
