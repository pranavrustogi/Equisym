package com.equisym.web;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.repository.query.Param;

//import java.io.UnsupportedEncodingException;

//import javax.mail.MessagingException;
//import javax.servlet.http.HttpServletRequest;

//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

//import com.equisym.model.Users;
import com.equisym.service.slot.SlotService;
import com.equisym.model.Slots;
import com.equisym.model.Users;
import com.equisym.model.Repository.SlotRepository;
//import com.equisym.service.UserServiceImpl;
//import com.equisym.service.Utility;
import com.equisym.web.dto.SlotCreationDto;
//import com.equisym.web.dto.UserRegistrationDto;

@Controller
public class SlotCreationController 
{
	private SlotService slotService;
	private SlotRepository slotRepository;
	private String slotId;

	public SlotCreationController(SlotService slotService, SlotRepository slotRepository) 
	{
		super();
		this.slotService = slotService;
		this.slotRepository = slotRepository;
	}
	
	@ModelAttribute("slot")
	public SlotCreationDto slotCreationDto()
	{
		return new SlotCreationDto();
	}
	
	//@GetMapping(value="/slot/")
	public String showSlotCreationForm(Model model,@ModelAttribute("slot")SlotCreationDto creationDto)
	{
		System.out.println("Creating slot");
		Slots slot = new Slots();
		slot.setFirstName(creationDto.getFirstName());
		slot.setLastName(creationDto.getLastName());
		slot.setEmail(creationDto.getEmail());
		slot.setRoleName(creationDto.getRoleName());
		slot.setContact(creationDto.getContact());
		model.addAttribute("slot",slot);
		return "slot";
	}
	@GetMapping(value="/slot")
	public String updateSlotForm(@RequestParam("id") String id, Model model, @ModelAttribute("slot")SlotCreationDto creationDto)
	{
		slotId = id;
		if (id.equalsIgnoreCase("0"))
		{
			System.out.println("Directly creating slot");
			return showSlotCreationForm(model , creationDto);
		}
		else
		{
		System.out.println("slot Update Id : "+id);
		Optional<Slots> slots = slotRepository.findById(Long.parseLong(id));
		Slots slotInfo = null;
		if(slots.isPresent())
		{
			slotInfo = slots.get();
		}
		model.addAttribute("slot", slotInfo);
		return "slot";
		}
	}

	
	
	@PostMapping(value="/slot")
	public String createSlot2(HttpServletRequest request,@ModelAttribute("slot")SlotCreationDto creationDto)
	{	
		

		if(slotId.equalsIgnoreCase("0"))
		{
			System.out.println("PostMapping method");
		
			slotService.save(creationDto);
		
			return "redirect:/slot_index";
		}
		else
		{
			System.out.println("Updating Id: "+ slotId);
			LocalDate date = LocalDate.parse(request.getParameter("date"));
			DayOfWeek day = date.getDayOfWeek();
			String landmark = request.getParameter("landmark");
			String zipCode = request.getParameter("zipCode");
			double latitude = Double.parseDouble(request.getParameter("latitude"));
			double longitude = Double.parseDouble(request.getParameter("longitude"));
			String timeIn = request.getParameter("timeIn");
			String timeOut = request.getParameter("timeOut");
			String address1 = request.getParameter("address1");
			String address2 = request.getParameter("address2");
			int capacity = Integer.parseInt(request.getParameter("capacity"));
			slotService.updateSlot(Long.parseLong(slotId), date, day, timeIn, timeOut,address1, address2, landmark, zipCode, latitude, longitude, capacity);
			return "redirect:/slot_index";
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
