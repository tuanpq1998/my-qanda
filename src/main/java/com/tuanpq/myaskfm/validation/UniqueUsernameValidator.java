/**
 * @author admin
 * @date 02-11-2019
 */

package com.tuanpq.myaskfm.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.tuanpq.myaskfm.service.UserService;


public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		return !userService.isUsernameExisted(username);
	}

}