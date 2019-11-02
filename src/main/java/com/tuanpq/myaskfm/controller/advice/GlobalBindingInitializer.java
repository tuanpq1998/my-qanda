/**
 * @author admin
 * @date 01-11-2019
 */

package com.tuanpq.myaskfm.controller.advice;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class GlobalBindingInitializer {

	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		StringTrimmerEditor string = new StringTrimmerEditor(true);
		binder.registerCustomEditor(String.class, string);
	}
}
