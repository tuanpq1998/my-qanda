/**
 * @author admin
 * @date 26-10-2019
 */

package com.tuanpq.myaskfm.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tuanpq.myaskfm.model.TempUser;
import com.tuanpq.myaskfm.service.UserService;
import com.tuanpq.myaskfm.utility.Constant;

@Controller
public class LogAndRegisterController {

	@Autowired
	private UserService userService;
	
	private boolean isLoggedIn(Principal principal) {
		return principal != null;
	}
	
	private String redirectToWall(String username) {
		return "redirect:/wall/" + username;
	}
	
	@GetMapping("/login")
	public String showLogin(Principal principal) {
		return isLoggedIn(principal) ? redirectToWall(principal.getName()) 
				: Constant.LOGIN_PAGE;
	}
	
	@GetMapping({"/index", "/"})
	public String showWelcomePage(Principal principal, Model model) {
		model.addAttribute("tempUser", new TempUser());
		return Constant.WELCOME_PAGE;
	}
	
	@PostMapping({"index", "/"})
	public String registerNew(Model model, Principal principal, 
			@Valid @ModelAttribute TempUser tempUser, BindingResult result) {
		if (isLoggedIn(principal)) 
			return redirectToWall(principal.getName());
		if (result.hasErrors()) 
			return Constant.WELCOME_PAGE;
		userService.registerNewAccount(tempUser);
		return "redirect:/" + Constant.LOGIN_URI + "?registered&name=" + tempUser.getUsername();
	}
	
}
