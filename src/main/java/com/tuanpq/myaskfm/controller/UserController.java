/**
 * @author admin
 * @date 29-10-2019
 */

package com.tuanpq.myaskfm.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tuanpq.myaskfm.entity.Question;
import com.tuanpq.myaskfm.entity.User;
import com.tuanpq.myaskfm.model.TempUser;
import com.tuanpq.myaskfm.service.QuestionService;
import com.tuanpq.myaskfm.service.UserService;
import com.tuanpq.myaskfm.utility.Constant;
import com.tuanpq.myaskfm.utility.NotificationAdder;
import com.tuanpq.myaskfm.validation.ValidationGroup.OnChangePassword;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	private List<ObjectError> getListContainsOnlyFirstError(
			BindingResult result) {
		List<ObjectError> list = new ArrayList<>();
		list.add(result.getAllErrors().get(0));
		return list;
	}
	
	@GetMapping("/questions")
	public String showQuestionsList(Principal principal, Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page) {
		
		Slice<Question> sliceQuestions = questionService
				.findAllQuestionsReceivedByUser(principal.getName(), page-1);
		model.addAttribute("questions", sliceQuestions);
		
		int totalQuestions = questionService.countQuestionsReceivedOfUser(principal.getName());
		model.addAttribute("numTotalQuestions", totalQuestions);
		model.addAttribute("numDigitTotalQuestions", String.valueOf(totalQuestions).length());
		
		questionService.setAllInListQuestionsSeen(sliceQuestions.getContent());
		
		NotificationAdder.addNumUnseenQuestionsToModel(questionService, model, principal);
		NotificationAdder.addNumUnseenAnswersToModel(questionService, model, principal);
		return Constant.LIST_Q_PAGE;
	}
	
	@PostMapping("/questions/{questionId}")
	public String answerThatQuestion(@PathVariable int questionId, 
			@RequestParam("answer") String answer) {
		if (answer == null)
			return "redirect:/" + Constant.LIST_Q_URI + "?failed";
		
		questionService.updateAnswerForQuestion(questionId, answer);
		return "redirect:/" + Constant.LIST_Q_URI + "?answered";
	}
	
	@GetMapping("/answers")
	public String showAnswersList(Principal principal, Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page) {
		Slice<Question> sliceAnswers = questionService
				.findAllQuestionsSentByUser(principal.getName(), page-1);
		model.addAttribute("answers", sliceAnswers);
		
		int totalAnswers = questionService.countQuestionsSentOfUser(principal.getName());
		model.addAttribute("numTotalAnswers", totalAnswers);
		model.addAttribute("numDigitTotalAnswers", String.valueOf(totalAnswers).length());
		
		questionService.setAllInListAnswersSeen(sliceAnswers.getContent());
		
		NotificationAdder.addNumUnseenQuestionsToModel(questionService, model, principal);
		NotificationAdder.addNumUnseenAnswersToModel(questionService, model, principal);
		return Constant.LIST_A_PAGE;
	}
	
	@GetMapping("/profile")
	public String showProfile(Model model, Principal principal) {
		if (model.getAttribute("user") == null) {
			User user = userService.findByUsername(principal.getName());
			model.addAttribute("user", user);
		}
		
		NotificationAdder.addNumUnseenQuestionsToModel(questionService, model, principal);
		NotificationAdder.addNumUnseenAnswersToModel(questionService, model, principal);
		return Constant.PROFILE_PAGE;
	}
	
	@PostMapping("/profile")
	public String updateUser(Principal principal, Model model, 
			@Valid @ModelAttribute User user, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("errorsValidation", result.getAllErrors());
			model.addAttribute("user", user);
			return showProfile(model, principal);
		}
		userService.updateUserInfo(principal.getName(), user);
		return "redirect:/" + Constant.WALL_URI + "/" + principal.getName() + "?edited";
	}
	
	@GetMapping("/change-password")
	public String showChangePasswordForm(Principal principal, Model model) {
		TempUser user = new TempUser();
		user.setUsername(principal.getName());
		model.addAttribute("tempUser", user);
		
		NotificationAdder.addNumUnseenQuestionsToModel(questionService, model, principal);
		NotificationAdder.addNumUnseenAnswersToModel(questionService, model, principal);
		return Constant.CHANGE_PASSWORD_PAGE;
	}
	
	@PostMapping("/change-password")
	public String changeUserPassword(Principal principal, Model model,
			@Validated(OnChangePassword.class) @ModelAttribute TempUser tempUser, 
			BindingResult result) {
		String authenticationName = principal.getName();
		if (result.hasErrors()) {
			List<ObjectError> list = getListContainsOnlyFirstError(result);
			model.addAttribute("errorsValidation", list);
			return showChangePasswordForm(principal, model);
		} else if (authenticationName.equals(tempUser.getUsername()))
			return showChangePasswordForm(principal, model);
		userService.updateUserPassword(principal.getName(), tempUser.getPassword());
		return "redirect:/" + Constant.WALL_URI + "/" + authenticationName + "?changed";
	}

}
