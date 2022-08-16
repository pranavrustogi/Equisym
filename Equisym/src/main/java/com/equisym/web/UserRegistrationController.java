package com.equisym.web;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.repository.query.Param;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.equisym.service.CryptoConverter;
import com.equisym.model.Users;
//import com.equisym.model.Users;
import com.equisym.service.UserService;
//import com.equisym.service.UserServiceImpl;
import com.equisym.service.Utility;
import com.equisym.web.dto.UserRegistrationDto;

@Controller

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
	
	//It will load registration form
	@GetMapping("/registration")
	public String showRegistrationForm()
	{
		return "registration";
	}
	
	// It will take the personal details of the user from the registration form.
	// Then it will check whether any user with email id or contact number is already saved in the database.
	// Once checked then it will send verification mail to user for self verification.
	// If user's role is "Trainer" or "Trainer & Trainee" then admin verification is to be done by administrator.
	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute("user")UserRegistrationDto registrationDto,HttpServletRequest request) throws Exception
	{
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		
		Users user1 = userService.checkEmail(email);
		Users user2 = userService.checkContact(contact);
		if(user1==null && user2==null)
		{
			String rolename = request.getParameter("roleName");
			System.out.println("Original role name: "+rolename);
			/*
			String encryptedText =  userService.encryptKey(rolename);
			String decryptedText = userService.decryptKey(userService.encryptKey(rolename));
			System.out.println("Encrypted key : "+ encryptedText);
			System.out.println("Decrypted key : "+ decryptedText);
			*/
		

			//System.out.println("Encrypted Key : "+ userService.encrypt("Si0b73v856tf1gh9",""));
			//System.out.println("Decrypted Key : "+userService.decryptKey(userService.encryptKey("Si0b73v856tf1gh9")));
			//String convertMessage = userService.convertMessage(request.getParameter("ssn"));
			//System.out.println("Encrypted Message : "+convertMessage);
			//System.out.println("Decrypted Message : "+userService.convertMessageAgain(convertMessage));
			userService.save(registrationDto);
			if(rolename.equalsIgnoreCase("Trainee"))
			{
				System.out.println("Hello Trainee");
				userService.setAdminVerifiedTrue(email);
			}
			/* ABC
			Users user = userService.checkEmail(email);
			CryptoConverter cry = new CryptoConverter();
			System.out.println(user.getSsn());
			 */
		
			//System.out.println(cry.convertToEntityAttribute(user.getSsn()));
			
			String siteURL= Utility.getSiteURL(request);
			userService.sendVerificationMail(registrationDto,siteURL);
			return "redirect:/registration?Success";
		}
		else
		{
		return "redirect:/registration?Fail";
		}
	}

	//It will check the verification code. Once verified then only you can sign in. 
	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code, @ModelAttribute("user")UserRegistrationDto registrationDto,Model model) 
	{
		boolean verified = userService.verify(registrationDto,code);
		String pageTitle = verified ? "Verification Succeeded!" : "Verification failed!";
		model.addAttribute("pageTitle", pageTitle);				
		return (verified ? "verify_success" : "verify_fail");
	}
		
	

	

 
}
