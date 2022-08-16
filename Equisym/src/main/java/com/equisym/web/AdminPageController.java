package com.equisym.web;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.equisym.model.Slots;
import com.equisym.model.Students;
import com.equisym.model.Users;
import com.equisym.model.Repository.SlotRepository;
import com.equisym.model.Repository.UserRepository;
import com.equisym.model.Repository.StudentRepository;
import com.equisym.service.UserService;
import com.equisym.service.Utility;
import com.equisym.service.slot.SlotService;
import com.equisym.service.student.StudentService;
import com.equisym.web.dto.SlotCreationDto;
import com.equisym.web.dto.StudentsDto;
import com.equisym.web.dto.UserRegistrationDto;

@Controller
//@RequestMapping
public class AdminPageController 
{
	private SlotService slotService;
	private UserService userService;
	private UserRepository userRepository;
	private SlotRepository slotRepository;
	private String role="";
	private List<Users> userPendingList;
	private String email;
	//private String emailId = SecurityContextHolder.getContext().getAuthentication().getName();

	public AdminPageController(SlotService slotService, StudentService studentService ,SlotRepository slotRepository,UserRepository userRepository ,StudentRepository studentRepository, UserService userService) 
	{
		super();
		this.slotService = slotService;
		this.userService = userService;
		this.slotRepository = slotRepository;
		this.userRepository = userRepository;
		
	}
	
	// In this function we are checking if user profile is administrator or not.
	@GetMapping("/admin_page")
	public String adminHome(Model model)
	{
		this.email = SecurityContextHolder.getContext().getAuthentication().getName();
		Users userInfo = userService.checkEmail(email);
		if((email.equalsIgnoreCase("anonymousUser")) || (!userInfo.getRoleName().equalsIgnoreCase("Admin")))
		{
			return "redirect:/login";
		}
		
		return "admin_page"; 
	}
	// Here we are getting the role from page then will get the list of users with pending verification and role and submit the list to page.
	@PostMapping("/admin_page")
	public String adminForm(Model model, HttpServletRequest request)
	{
		
		String role = request.getParameter("role");
		userPendingList = userRepository.findByAdminVerified(role,false);
		model.addAttribute("pending_list",userPendingList);
		return "admin_page";
	}
	
	//It will take the Id and call adminApprove function of user service interface taking id as argument.
	//adminApprove function will change admin_verified to true to the same id and will send verification successful mail to user.
	//After this the user can logs in.
	@GetMapping("/admin_page/approve/{id}")
	public String approve(@PathVariable(name= "id") Long id, Model model)
	{
		
		userService.adminAprrove(id);
		userPendingList = userRepository.findByAdminVerified(role,false);
		model.addAttribute("pending_list",userPendingList);
		return "admin_page";
	}
	
	//It will take the Id and call adminDeny function of user service interface taking id as argument.
	//adminDeny function will check remove the user from database if role is "trainer".
	// This function will role to trainee if user's previous role is both "Trainer & Trainee" 
	//User will be informed via mail.
	@GetMapping("/admin_page/deny/{id}")
	public String deny(@PathVariable(name= "id") Long id, Model model)
	{
		
		userService.adminDeny(id);
		userPendingList = userRepository.findByAdminVerified(role,false);
		model.addAttribute("pending_list",userPendingList);
		return "admin_page";
	}
	
	// This will call Status Page once clicked on check status on administrator page.
	@GetMapping("/admin_page2")
	public String adminHome2(Model model)
	{
		this.email = SecurityContextHolder.getContext().getAuthentication().getName();
		Users userInfo = userService.checkEmail(email);
		if((email.equalsIgnoreCase("anonymousUser")) || (!userInfo.getRoleName().equalsIgnoreCase("Admin")))
		{
			return "redirect:/login";
		}
		
		return "admin_page2"; 
	}
	
	// Here we are getting the role from page then will get the list of users with their administrator verified status.
	@PostMapping("/admin_page2")
	public String adminForm2(Model model, HttpServletRequest request)
	{
		
			role = request.getParameter("rolename");
		
		List<Users> userList = userRepository.findByAdminVerified(role);
		model.addAttribute("pending_list",userList);
		return "admin_page2";
	}
	

}