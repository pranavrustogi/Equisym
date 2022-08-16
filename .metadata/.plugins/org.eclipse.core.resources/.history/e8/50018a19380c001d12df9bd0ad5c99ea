package com.equisym.web;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.equisym.model.Users;
import com.equisym.service.UserService;
import com.equisym.service.Utility;
import com.equisym.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/forgot")
public class forgetPasswordController 
{
	private UserService userService;

	public forgetPasswordController(UserService userService) 
	{
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto()
	{
		return new UserRegistrationDto();
	
	}
	
	@GetMapping
	public String forgetPasswordForm()
	{
		return "forgot";
	}
	
	
	@PostMapping
	public String resetUserPassword(Users user,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException
	{
	    String email = request.getParameter("email");
		Users userInfo = userService.checkEmail(email);
		if(userInfo!=null)
		{
			if(userInfo.isEnabled())
			{
				String siteURL= Utility.getSiteURL(request);
				userService.sendResetPasswordMail(user,siteURL);
				return "redirect:/forgot?Success";
			}
			else
			{
				return "redirect:/forgot?VerifyFailure";
			}
		}
		else
		{
			return "redirect:/forgot?Fail";
		}
	}
}
