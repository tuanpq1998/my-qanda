/**
 * @author admin
 * @date 01-11-2019
 */

package com.tuanpq.myaskfm.controller.advice;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class GlobalBindingInitializer {
	
	private PropertyEditorSupport noTrimPropertyEditor = new PropertyEditorSupport() {
        @Override
        public void setAsText(String text) {
            super.setValue(text);
        }
    };
	
	private static final String[] LIST_NOTRIM_FIELDS = {"password",
			"rePassword", "oldPassword"};
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		
		for (String field : LIST_NOTRIM_FIELDS)
			binder.registerCustomEditor(String.class, field, noTrimPropertyEditor);
	}
}
