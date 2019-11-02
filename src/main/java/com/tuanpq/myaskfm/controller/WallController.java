/**
 * @author admin
 * @date 28-10-2019
 */

package com.tuanpq.myaskfm.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.tuanpq.myaskfm.entity.Question;
import com.tuanpq.myaskfm.entity.User;
import com.tuanpq.myaskfm.service.QuestionService;
import com.tuanpq.myaskfm.service.UserService;
import com.tuanpq.myaskfm.utility.Constant;
import com.tuanpq.myaskfm.utility.NotificationAdder;
import com.tuanpq.myaskfm.validation.ValidationGroup.OnAsk;

@Controller
@RequestMapping("/wall")
public class WallController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuestionService questionService;
	
	public boolean isLoggedInUser(Principal principal) {
		return principal != null;
	}
	
	private boolean isAskingSelf(String usernameAnswer, String usernameAsk){
		return usernameAsk.equals(usernameAnswer);
	}

	@GetMapping("/{username}")
	public String showWall(@PathVariable String username,
			 @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			 Model model, Principal principal) {
		if (isLoggedInUser(principal)){
			NotificationAdder.addNumUnseenQuestionsToModel(questionService, model, principal);
			NotificationAdder.addNumUnseenAnswersToModel(questionService, model, principal);
		}
		
		User user = userService.findByUsername(username);
		
		if (user == null) {
			throw new ResponseStatusException(
			           HttpStatus.NOT_FOUND, "Username not found");
		}
		model.addAttribute("user", user);
		
		Slice<Question> answeredQuestions = 
				questionService.findAnsweredQuestionsByUserId(user.getId(), (int)page-1);
		model.addAttribute("answeredQuestions", answeredQuestions);
		
		int answered = questionService.countAnsweredQuestionsByUserId(user.getId());
		int total = questionService.countQuestionsReceivedOfUser(user.getId());
		model.addAttribute("numAnsweredQuestions", answered);
		model.addAttribute("numTotalQuestions", total);
		model.addAttribute("numDigitTotalQuestions", String.valueOf(total).length());
		
		return Constant.WALL_PAGE;
	}
	
	@GetMapping("/{username}/ask")
	public String showAskForm(@PathVariable String username, Model model, 
			Principal principal) {
		if (isLoggedInUser(principal)){
			NotificationAdder.addNumUnseenQuestionsToModel(questionService, model, principal);
			NotificationAdder.addNumUnseenAnswersToModel(questionService, model, principal);
		}
		
		User user = userService.findByUsername(username);
		model.addAttribute("user", user);
		
		Question question = new Question();
		question.setIncognitoAsk(true);
		model.addAttribute("question", question);
		
		return Constant.ASK_PAGE;
	}
	
	@PostMapping("/{username}/ask")
	public String addNewQuestion(@PathVariable String username, 
			Model model, Principal principal, 
			@Validated(OnAsk.class) @ModelAttribute Question question, BindingResult result) {
		if (result.hasErrors()){
			model.addAttribute("errorsValidation", result.getAllErrors());
			return showAskForm(username, model, principal);
		}
		
		User userAnswer = userService.findByUsername(username);
		User userAsk = null;
		
		if (!isLoggedInUser(principal) || isAskingSelf(principal.getName(), username)) 
			question.setIncognitoAsk(true);
		if (isLoggedInUser(principal)) 
			userAsk = userService.findByUsername(principal.getName());
		
		questionService.addNew(question, userAnswer, userAsk);
		return "redirect:/" + Constant.WALL_URI + "/" + username + "?asked";
	}
	
}
