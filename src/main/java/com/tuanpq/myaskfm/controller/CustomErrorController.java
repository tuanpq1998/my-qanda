/**
 * @author admin
 * @date 02-11-2019
 */

package com.tuanpq.myaskfm.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuanpq.myaskfm.utility.Constant;

@Controller
public class CustomErrorController implements ErrorController {
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@RequestMapping("/error")
	public String handleError(Model model, HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    Integer statusCode = null;
	    if (status != null) {
	        statusCode = Integer.valueOf(status.toString());
	     
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	            return Constant.ERROR_404_PAGE;
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            return Constant.ERROR_500_PAGE;
	        }
	        else if(statusCode == HttpStatus.FORBIDDEN.value()) {
	            return Constant.ERROR_403_PAGE;
	        }
	    }
	    return "/errors/error";
	}
}
