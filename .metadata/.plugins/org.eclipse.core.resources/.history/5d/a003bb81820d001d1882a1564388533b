package com.equisym.web;

//import java.io.UnsupportedEncodingException;

//import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.equisym.model.Users;
//import com.equisym.model.Repository.UserRepository;
import com.equisym.service.UserService;
//import com.equisym.service.Utility;
import com.equisym.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/reset")
public class ResetPasswordController 
{
	private UserService userService;
	

	public ResetPasswordController(UserService userService) 
	{
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto()
	{
		return new UserRegistrationDto();
	
	}
	/*
	@GetMapping
	public String showVerify()
	{
		return "verify";
	}
	*/
	
	
	@GetMapping
	public String resetPassword(@Param("code") String code, Model model) 
	{
		Users user = userService.getResetPasswordCode(code);
		model.addAttribute("code", code);
		if(user!=null)
		{
			return "reset";
		}
		else
		{
			return "reset_fail";
		}
	}
	
	@PostMapping
	public String processResetPassword(HttpServletRequest request, Model model) 
	{
		String code = request.getParameter("code");
		String newPassword = request.getParameter("password");
		
		Users user = userService.getResetPasswordCode(code);
		userService.updatePassword(user,newPassword);
		/*return "reset_success";*/
		return "login";
	}
}
