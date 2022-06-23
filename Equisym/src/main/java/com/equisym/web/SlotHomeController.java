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
public class SlotHomeController 
{
	private SlotService slotService;
	private StudentService studentService;
	private SlotRepository slotRepository;
	private UserRepository userRepository;
	private StudentRepository studentRepository;
	private String SlotCourse;
	private LocalDate SlotDate;
	private String timeIn;
	private String timeOut;
	private String email;
	private String address1;
	private String address2;
	private String landmark;
	private String zipCode;
	private List<String> scourse;
	private List<LocalDate> sSlotDate;
	private List<Slots> SlotTime;
	//private String emailId = SecurityContextHolder.getContext().getAuthentication().getName();

	public SlotHomeController(SlotService slotService, StudentService studentService ,SlotRepository slotRepository,UserRepository userRepository ,StudentRepository studentRepository) 
	{
		super();
		this.slotService = slotService;
		this.studentService = studentService;
		this.slotRepository = slotRepository;
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/slot_home")
	public String SlotHome(Model model)
	{
		
		this.email = SecurityContextHolder.getContext().getAuthentication().getName();
		Users user = userRepository.findByEmail(email);
		
		if((email.equalsIgnoreCase("anonymousUser")) || (user.getRoleName().equalsIgnoreCase("Trainer")))
		{
			//System.out.println("Email is null : "+email);
			return "redirect:/login";
		}
		
		
		scourse= slotRepository.getCourses();
		model.addAttribute("scourse",scourse);
		return "slot_home"; 
	}
	@PostMapping("/slot_home")
	public String home(Model model)
	{
		return SlotHome(model);
	}
	
	@PostMapping("/slot_home/course")
	public String SlotBook2(Model model, HttpServletRequest request)
	{
		
		this.SlotCourse = request.getParameter("slotd");
		sSlotDate = slotRepository.getSlotDate(SlotCourse);
		model.addAttribute("scourse",scourse);
		model.addAttribute("sSlotDate", sSlotDate);
		return "slot_home";
	}
	
	@GetMapping("/slot_home/course")
	public String slotBook3(Model model, HttpServletRequest request)
	{
		this.SlotDate = LocalDate.parse(request.getParameter("slotdate"));
		SlotTime = slotRepository.getSlotTime(SlotCourse,SlotDate);
		model.addAttribute("scourse",scourse);
		model.addAttribute("sSlotDate", sSlotDate);
		model.addAttribute("sSlotTime", SlotTime);
		return "slot_home";
		
	}
	@RequestMapping("/slot_home/time")
	public String slotBooking(Model model, HttpServletRequest request)
	{
		System.out.println("I am slotBooking");
		long slot_id = Long.parseLong(request.getParameter("slottime"));
		this.timeIn = slotRepository.getTimeInById(slot_id);
		this.timeOut = slotRepository.getTimeOutById(slot_id);
		System.out.println("Slot Start Time : "+timeIn);
		System.out.println("Slot End Time : "+timeOut);
		
		List<Students> booked = studentRepository.getSlots(email,SlotDate,timeIn,timeOut);
		
		if (booked.size()>0)
			return "book_error";
		
		
		List<Slots> avail_slots = slotRepository.getAvailableSlots(SlotCourse,SlotDate,timeIn,timeOut);
		model.addAttribute("availableSlots",avail_slots);
		return "slot_home";
	}
	@PostMapping(value="/slot_home/book")
	public String book(StudentsDto studentDto, HttpServletRequest request)
	{
		this.address1 = request.getParameter("address1");
		System.out.println("Address Line 1 : "+address1);
		this.address2 = request.getParameter("address2");
		System.out.println("Address Line 2 : "+address2);
		this.landmark = request.getParameter("landmark");
		System.out.println("Landmark : "+landmark);
		this.zipCode = request.getParameter("zipCode");
		System.out.println("ZIP Code : "+zipCode);
		
		Long id = slotRepository.getId(SlotCourse,SlotDate,timeIn,timeOut,address1,address2,landmark,zipCode);
		Slots slotInfo= slotService.decreaseCapacity(id);
		studentDto.slotDetails(slotInfo);
		studentService.save(studentDto);
		studentService.sendEmail(email);
		
		return "book_success";
	}
	
	/*
	@GetMapping("/availableSlots")
	public String available(Model model)
	{
		
		List<Students> booked = studentRepository.getSlots(email,SlotDate,timeIn,timeOut);
		
		if (booked.size()>0)
			return "book_error";
		
		
		List<Slots> avail_slots = slotRepository.getAvailableSlots(SlotCourse,SlotDate,timeIn,timeOut);
		model.addAttribute("availableSlots",avail_slots);
		return "available_slots";
	}
	@GetMapping("/availableSlots/book/{id}")
	public String booking(@PathVariable("id") String id, StudentsDto studentDto)
	{
		Slots slotInfo= slotService.decreaseCapacity(Long.parseLong(id));
		studentDto.slotDetails(slotInfo);
		studentService.save(studentDto);
		studentService.sendEmail(email);
		return "book_success";
	}
	*/
		
}
