/**
 * @author admin
 * @date 30-10-2019
 */

package com.tuanpq.myaskfm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.tuanpq.myaskfm.service.QuestionService;

@Component
public class Guard {
	
	@Autowired
    private QuestionService questionService;
	
	public boolean checkUserAndQuestion(Authentication authentication, int questionId) {
		if (authentication instanceof AnonymousAuthenticationToken)
			return false;
		return questionService.isUsernameOwnerUnansweredQuestion(authentication.getName(),
				questionId);
	}
	
}
