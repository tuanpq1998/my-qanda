/**
 * @author admin
 * @date 02-11-2019
 */

package com.tuanpq.myaskfm.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {

	public String message() default "Invalid username!";
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
}