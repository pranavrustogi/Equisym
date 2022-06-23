package com.equisym.web;

import java.io.UnsupportedEncodingException;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.equisym.model.Users;
//import com.equisym.model.Users;
import com.equisym.service.UserService;
//import com.equisym.service.UserServiceImpl;
import com.equisym.service.Utility;
import com.equisym.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController 
{
	private UserService userService;

	public UserRegistrationController(UserService userService) 
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
	public String showRegistrationForm()
	{
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user")UserRegistrationDto registrationDto,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException
	{
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		
		Users user1 = userService.checkEmail(email);
		Users user2 = userService.checkContact(contact);
		if(user1==null && user2==null)
		{
			userService.save(registrationDto);
			String siteURL= Utility.getSiteURL(request);
			userService.sendVerificationMail(registrationDto,siteURL);
			return "redirect:/registration?Success";
		}
		else
		{
		return "redirect:/registration?Fail";
		}
	}
	
/*
	public String verifyAccount(@Param("code") String code, @ModelAttribute("user")UserRegistrationDto registrationDto,Model model)
	{
		
	
		boolean verified = userService.verify(registrationDto,code);
		userService.save(registrationDto);
		String pageTitle = verified ? "Verification Succeeded!" : "Verification failed!";
		model.addAttribute("pageTitle", pageTitle);
		return "register/"+ (verified ? "verify_success" : "verify_fail"); 
		
		
	}*/
	
	

 
}
