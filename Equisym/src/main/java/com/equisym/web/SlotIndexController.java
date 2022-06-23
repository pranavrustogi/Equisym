package com.equisym.web;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

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

import com.equisym.model.Slots;
import com.equisym.model.Users;
import com.equisym.model.Repository.SlotRepository;
import com.equisym.model.Repository.UserRepository;
import com.equisym.service.UserService;
import com.equisym.service.Utility;
import com.equisym.service.slot.SlotService;
import com.equisym.web.dto.UserRegistrationDto;

@Controller
//@RequestMapping
public class SlotIndexController 
{
	private SlotService slotService;
	private SlotRepository slotRepository;
	private UserRepository userRepository;
	private List<Slots> slotList;
	private Long slotId;
	private String email;

	public SlotIndexController(SlotService slotService, SlotRepository slotRepository,UserRepository userRepository) 
	{
		super();
		this.slotService = slotService;
		this.slotRepository = slotRepository;
		this.userRepository = userRepository;
	}

	
	
	@GetMapping("/slot_index")
	public String SlotIndex(Model model)
	{
		
		this.email = SecurityContextHolder.getContext().getAuthentication().getName();
		Users user = userRepository.findByEmail(email);
		
		if((email.equalsIgnoreCase("anonymousUser")) || (user.getRoleName().equalsIgnoreCase("Trainee")))
		{
			//System.out.println("Email is null : "+email);
			return "redirect:/login";
		}
		
		List<Slots> slist2 = slotRepository.findByEmail(email);
		model.addAttribute("slist2",slist2);
		
		
		return "slot_index"; 
	}
	@PostMapping("/slot_index/create")
	public String SlotCreation()
	{
		Long id = (long) 0;
		System.out.println("Slot Index Create Controller");
		return "redirect:/slot?id="+id;
	}
	
	@PostMapping("/slot_index/update")
	public String SlotUpdate(Model model, HttpServletRequest request)
	{
		

		Long slotId = Long.parseLong(request.getParameter("slotd"));
		System.out.println("Slot Index Update Post Controller");
		System.out.println("Id of slot : "+slotId);
		return "redirect:/slot?id="+slotId;
	
		
	}
	
	@PostMapping("/slot_index/delete")
	public String SlotDelete(Model model, HttpServletRequest request)
	{
		Long slotId = Long.parseLong(request.getParameter("slot_details2"));
		slotRepository.deleteById(slotId);
		return "redirect:/slot_index?Success";
		
	}	
}