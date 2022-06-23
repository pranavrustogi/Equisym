package com.equisym.web;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.equisym.service.UserService;
import com.equisym.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/verify")
public class VerifyController 
{
	private UserService userService;

	public VerifyController(UserService userService) 
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
	public String verifyAccount(@Param("code") String code, @ModelAttribute("user")UserRegistrationDto registrationDto,Model model) 
	{
		boolean verified = userService.verify(registrationDto,code);
		String pageTitle = verified ? "Verification Succeeded!" : "Verification failed!";
		model.addAttribute("pageTitle", pageTitle);				
		return (verified ? "verify_success" : "verify_fail");
	}
}
