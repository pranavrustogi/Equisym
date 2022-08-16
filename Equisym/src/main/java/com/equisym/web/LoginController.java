package com.equisym.web;

//import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.equisym.model.Users;
//import com.equisym.model.Users;
import com.equisym.service.UserService;
import com.equisym.web.dto.SlotCreationDto;
import com.equisym.web.dto.StudentsDto;
//import com.equisym.web.dto.UserRegistrationDto;

@Controller
public class LoginController 
{
	

	private UserService userService;
	public LoginController(UserService userService) 
	{
		super();
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String login() 
	{
		//System.out.println(email);
		return "login";
	}
	
	@GetMapping("/conductUs")  
	public String contactUs() 
	{
		return "conductUs";
	}

	@GetMapping("/partnersInfo")
	public String partnersInfo() 
	{
		return "partnersInfo";
	}
	
	@GetMapping("/index")
	public String index() 
	{
		return "index";
	} 
	
	// This function prevents user who didn't verified their email id or they are not administrator verified.
	// User with Role "Trainer" will reach to slot index page where they can create/update and delete slots.
	// User with Role "Trainee" will reach to slot home page where they can book the available slots according to available sports slots, date &time.
	// User with Role "Trainer & Trainee"  will reach to slot main page where if they have choice to go to trainer page or trainee page. 
	
	@GetMapping("/")
	public String loginPage(HttpServletRequest request, Authentication authentication)
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Users userInfo = userService.checkEmail(username);
		if(userInfo.isEnabled()==true )
		{
			
			if(userInfo.isAdminVerified()==true)
			{
				if(userInfo.getRoleName().equalsIgnoreCase("Trainer"))
					 
				{
					SlotCreationDto.userDetails(userInfo);
					//return "redirect:/slot";
					return "redirect:/slot_index";
				}
				else if(userInfo.getRoleName().equalsIgnoreCase("Trainee"))
				{
					StudentsDto.userDetails(userInfo);
					return "redirect:/slot_home";
				}
				else if(userInfo.getRoleName().equalsIgnoreCase("Admin"))
				{
					return "redirect:/admin_page";
				}
				
				else
				{
					SlotCreationDto.userDetails(userInfo);
					StudentsDto.userDetails(userInfo);
					return "redirect:/slot_main";
				}
			}
			else
			{
				return "redirect:/login?Fail2";
			}
			

		}
		else
		{
			return "redirect:/login?Fail";
		}
		
	}
}
	
	