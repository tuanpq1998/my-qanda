/**
 * @author admin
 * @date 02-11-2019
 */

package com.tuanpq.myaskfm.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuanpq.myaskfm.service.UserService;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

	private String firstFieldName;
	private String secondFieldName;
	private String message;
	private boolean isUserValidate;
	
	@Autowired
	private UserService userService;

	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
		message = constraintAnnotation.message();
		isUserValidate = constraintAnnotation.isUserValidate();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid = true;
		try {
			Object firstObj = new BeanWrapperImpl(value)
					.getPropertyValue(firstFieldName);
			Object secondObj = new BeanWrapperImpl(value)
					.getPropertyValue(secondFieldName);

			if (isUserValidate){
				String password = firstObj.toString();
				String username = secondObj.toString();
				valid = userService.isPasswordCorrect(username, password);
			} else valid = firstObj == null && secondObj == null
					|| firstObj != null && firstObj.equals(secondObj);
		} catch (Exception ignore) {
			// we can ignore
		}
		if (!valid) {
			context.buildConstraintViolationWithTemplate(message)
					.addPropertyNode(firstFieldName).addConstraintViolation()
					.disableDefaultConstraintViolation();
		}

		return valid;
	}

}