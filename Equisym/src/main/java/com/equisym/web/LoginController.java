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
	public String contactus() 
	{
		return "conductUs";
	}

	@GetMapping("/partnersInfo")
	public String partnersinfo() 
	{
		return "partnersInfo";
	}
	
	@GetMapping("/index")
	public String index() 
	{
		return "index";
	} 
	
	@GetMapping("/")
	public String loginpage(HttpServletRequest request, Authentication authentication)
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Users userInfo = userService.checkEmail(username);
		if(userInfo.isEnabled()==true)
		{
			
			if(userInfo.getRoleName().equalsIgnoreCase("Trainer"))
			 
			{
				SlotCreationDto.userDetails(userInfo);
				//return "redirect:/slot";
				return "redirect:/slot_index";
			}
			else
			{
				StudentsDto.userDetails(userInfo);
				return "redirect:/slot_home";
			}

		}
		else
		{
			return "redirect:/login?Fail";
		}
		
	}
}
	
	