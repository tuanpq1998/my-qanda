/**
 * @author admin
 * @date 01-11-2019
 */

package com.tuanpq.myaskfm.utility;

import java.security.Principal;

import org.springframework.ui.Model;

import com.tuanpq.myaskfm.service.QuestionService;

public class NotificationAdder {

	public static void addNumUnseenQuestionsToModel(QuestionService questionService, 
			Model model, Principal principal) {
		int numUnseenQuestions = 
				questionService.countUnseenQuestionsOfUser(principal.getName());
		model.addAttribute("numUnseenQuestions", numUnseenQuestions);
	}
	
	public static void addNumUnseenAnswersToModel(QuestionService questionService, 
			Model model, Principal principal) {
		int numUnseenAnswers = 
				questionService.countUnseenAnswersOfUser(principal.getName());
		model.addAttribute("numUnseenAnswers", numUnseenAnswers);
	}
}
