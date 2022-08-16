package com.equisym.web;

import java.io.UnsupportedEncodingException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.View;

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
import com.equisym.model.Repository.StudentRepository;
import com.equisym.model.Repository.UserRepository;
import com.equisym.service.UserService;
import com.equisym.service.Utility;
import com.equisym.service.slot.SlotService;
import com.equisym.service.student.StudentService;
import com.equisym.web.dto.SlotCreationDto;
import com.equisym.web.dto.StudentsDto;
import com.equisym.web.dto.UserRegistrationDto;

@Controller
//@RequestMapping
public class SlotController 
{
	private String TempData[] ;
	private SlotService slotService;
	private SlotRepository slotRepository;
	private UserRepository userRepository;
	private StudentService studentService;
	private Long slotId;
	private String slot_Id;
	private String email;
	private StudentRepository studentRepository;
	private Users user;
	private String SlotCourse;
	private LocalDate SlotDate;
	private String timeIn;
	private String timeOut;
	private String address1;
	private String address2;
	private Long slot_id;
	private Long add_id;
	private Long updateId;
	private String landmark;
	private String zipCode;
	private String defaultAddress1 = "";
	private String defaultAddress2 = "";
	private String defaultLandmark = "";
	private String defaultCity="";
	private String defaultState="";
	private String defaultZipCode = "";
	private double defaultLongitude = 22.49682;
	private double defaultLatitude = 78.80045;
	private List<String> scourse;
	private List<LocalDate> sSlotDate;
	private List<Slots> sSlotTime;
	private List<Slots> sSlotAddress;
	private String city;
	private String state;

	public SlotController(StudentRepository studentRepository,StudentService studentService,SlotService slotService, SlotRepository slotRepository,UserRepository userRepository) 
	{
		super();
		this.slotService = slotService;
		this.slotRepository = slotRepository;
		this.userRepository = userRepository;
		this.studentService = studentService;
		this.studentRepository = studentRepository;
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
          this.email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(email.equalsIgnoreCase("anonymousUser"))
		{
			return "redirect:/login";
		}
	
		
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
		return "redirect:/slot_index?Delete_Success";
		
	}
	
	//Slot Creation Controller
	
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
		slot_Id = id;
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
		this.updateId = Long.parseLong(id);
		return "slot";
		}
	}

	
	
	@PostMapping(value="/slot")
	public String createSlot2(HttpServletRequest request,@ModelAttribute("slot")SlotCreationDto creationDto)
	{	
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if(slot_Id.equalsIgnoreCase("0"))
		{
			System.out.println("PostMapping method");
			LocalDate date = LocalDate.parse(request.getParameter("date"));
			String start = request.getParameter("timeIn");
			String end = request.getParameter("timeOut");
			
			
			List<Slots> slot = slotRepository.getSlots(email,date);
			for(int i=0;i<slot.size();i++)
			{
				boolean result = slotService.checkOverLap(slot.get(i).getTimeIn(),slot.get(i).getTimeOut(),start,end);
				if(result==true)
			
					
				return "redirect:/slot_index?Error";

			}
			
			slotService.save(creationDto);
		
			return "redirect:/slot_index?Created";
		}
		else
		{
			System.out.println("Updating Id: "+ updateId);
			LocalDate date = LocalDate.parse(request.getParameter("date"));
			DayOfWeek day = date.getDayOfWeek();
			
			double latitude = Double.parseDouble(request.getParameter("latitude"));
			double longitude = Double.parseDouble(request.getParameter("longitude"));
			String timeIn = request.getParameter("timeIn");
			String timeOut = request.getParameter("timeOut");
			String address1 = request.getParameter("address1");
			String address2 = request.getParameter("address2");
			String landmark = request.getParameter("landmark");
			String zipCode = request.getParameter("zipCode");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			int capacity = Integer.parseInt(request.getParameter("capacity"));
			List<Slots> slot = slotRepository.getSlots(email,date);
			for(int i=0;i<slot.size();i++)
			{
				boolean result = slotService.checkOverLap(slot.get(i).getTimeIn(),slot.get(i).getTimeOut(),timeIn,timeOut);
				
				if(result==true && slot.get(i).getId() != updateId)
				//	return "error2";
					return "redirect:/slot_index?Error2";
			}
			slotService.updateSlot(Long.parseLong(slot_Id), date, day, timeIn, timeOut,address1, address2, landmark, zipCode,city,state,latitude, longitude, capacity);
			return "redirect:/slot_index?Updated";
		}
	}
	
	
	// Slot Home Controller
	
	@GetMapping("/slot_home")
	public String SlotHome(Model model1)
	{
		
		this.email = SecurityContextHolder.getContext().getAuthentication().getName();
		this.user = userRepository.findByEmail(email);
		
		if((email.equalsIgnoreCase("anonymousUser")) || (user.getRoleName().equalsIgnoreCase("Trainer")))
		{
			
			return "redirect:/login";
		}
		
		if(user.getRoleName().equalsIgnoreCase("Trainer & Trainee"))
		{
			List<String> trainerCourses = slotRepository.getCourses(email);
			List<String> allCourses = slotRepository.getCourses();
			System.out.println("Trainer & Tranee list is empty");
			for(int i=0;i<trainerCourses.size();i++)
			{
				System.out.print(trainerCourses.get(i)+ " ");
			}
			System.out.println();
			for(int i=0;i<allCourses.size();i++)
			{
				System.out.print(allCourses.get(i)+ " ");
			}
			
			scourse= slotService.getDifferentCourses(trainerCourses,allCourses);
			for(int i=0;i<scourse.size();i++)
			{
				System.out.print(scourse.get(i)+ " ");
			}
			System.out.println();
			model1.addAttribute("scourse",scourse);
			return "slot_home";
		}
		scourse= slotRepository.getCourses();
		model1.addAttribute("scourse",scourse);
		return "slot_home"; 
	}
	@PostMapping("/slot_home")
	public String home(Model model2,HttpServletRequest request, StudentsDto studentDto)
	{
		String course = request.getParameter("slotcourse");
		String date = request.getParameter("slotdate");
		String add = request.getParameter("slotaddress");
		String add1 = request.getParameter("address1");
		String add2 = request.getParameter("address2");
		String land = request.getParameter("landmark");
		String zip = request.getParameter("zipCode");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		
		String id = request.getParameter("slottime");
		return slotBooking(model2,course,date,id,add,add1,add2,land,zip,city,state,studentDto);
	}

	private String slotBooking(Model model2, String course, String date, String id, String address, String add1, String add2, String land,
			String zip, String city, String state, StudentsDto studentDto) 
	{
		if(!course.isEmpty() && date.isEmpty() && id.isEmpty() && address.isEmpty() && add1.isEmpty() && add2.isEmpty() && land.isEmpty() && zip.isEmpty() && city.isEmpty() && state.isEmpty() )
		{
			this.SlotCourse = course;
			sSlotDate = slotRepository.getSlotDate(SlotCourse);
			model2.addAttribute("scourse",scourse);
			model2.addAttribute("sSlotDate", sSlotDate);
			System.out.println("First if condition");
		}
		else if(!course.isEmpty() && !date.isEmpty() && id.isEmpty() && address.isEmpty() && add1.isEmpty() && add2.isEmpty() && land.isEmpty() && zip.isEmpty() && city.isEmpty() && state.isEmpty())
		{
				System.out.println("Second else  if condition");
				this.SlotDate = LocalDate.parse(date);
				sSlotTime = slotRepository.getSlotTime(course,LocalDate.parse(date));
				model2.addAttribute("scourse",scourse);
				model2.addAttribute("sSlotDate", sSlotDate);
				model2.addAttribute("sSlotTime", sSlotTime);

			
		}
		else if (!course.isEmpty() && !date.isEmpty() && !id.isEmpty() && address.isEmpty() && add1.isEmpty() && add2.isEmpty() && land.isEmpty() && zip.isEmpty() && city.isEmpty() && state.isEmpty())
		{
				System.out.println("Third else  if condition");
			
				model2.addAttribute("scourse",scourse);
				model2.addAttribute("sSlotDate", sSlotDate);
				model2.addAttribute("sSlotTime", sSlotTime);
				this.slot_id = Long.parseLong(id);
				this.timeIn = slotRepository.getTimeInById(slot_id);
				this.timeOut = slotRepository.getTimeOutById(slot_id);
				System.out.println("Time In : "+timeIn);
				System.out.println("Time Out : "+timeOut);
				Students student = studentRepository.getSlots(email,LocalDate.parse(date),timeIn,timeOut);
				
				if (student!=null)
					return "redirect:/slot_home?Fail";
				else
				{
					this.sSlotAddress = slotRepository.getAvailableSlots(course,LocalDate.parse(date),timeIn,timeOut);
					model2.addAttribute("scourse",scourse);
					model2.addAttribute("sSlotDate", sSlotDate);
					model2.addAttribute("sSlotTime", sSlotTime);
					model2.addAttribute("sSlotAddress", sSlotAddress);
				}
			
		}
		else if (!course.isEmpty() && !date.isEmpty() && !id.isEmpty() && !address.isEmpty() && add1.isEmpty() && add2.isEmpty() && land.isEmpty() && zip.isEmpty() && city.isEmpty() && state.isEmpty())
		{
				System.out.println("Fourth else  if condition");
			
				model2.addAttribute("scourse",scourse);
				model2.addAttribute("sSlotDate", sSlotDate);
				model2.addAttribute("sSlotTime", sSlotTime);
				model2.addAttribute("sSlotAddress", sSlotAddress);
				this.add_id = Long.parseLong(address);
				Slots slot = null;
				Optional<Slots> optionalSlot = slotRepository.findById(add_id);
				if(optionalSlot.isPresent())
				{
					slot = optionalSlot.get();
				}
				defaultAddress1 =  slotService.getAddress1(slot);
				defaultAddress2 = slotService.getAddress2(slot);
				defaultLandmark = slotService.getLandmark(slot);
				defaultCity = slotService.getCity(slot);
				defaultState = slotService.getState(slot);
				defaultZipCode = slotService.getZipCode(slot);
				defaultLatitude = slotService.getLatitude(slot);
				defaultLongitude = slotService.getLongitude(slot);
				
				model2.addAttribute("add1",defaultAddress1);
				model2.addAttribute("add2",defaultAddress2);
				model2.addAttribute("land",defaultLandmark);
				model2.addAttribute("zip",defaultZipCode);
				model2.addAttribute("city",defaultCity);
				model2.addAttribute("state",defaultState);
				model2.addAttribute("lati",defaultLatitude);
				model2.addAttribute("longi",defaultLongitude);
		}
		
		else if(!course.isEmpty() && !date.isEmpty() && !(String.valueOf(id)).isEmpty() && !add1.isEmpty() && !add2.isEmpty() && !land.isEmpty() && !zip.isEmpty() && !city.isEmpty() && !state.isEmpty())		
		{
			
				this.address1 = add1;
				this.address2 = add2;
				this.landmark = land;
				this.zipCode = zip;
				this.city = city;
				this.state = state;
				System.out.println("Course : "+SlotCourse);
				System.out.println("Slot date : "+SlotDate.toString());
				System.out.println("Start time : "+timeIn);
				System.out.println("End time : "+timeOut);
				System.out.println("Address Line 1 : "+add1);
				System.out.println("Address Line 2 : "+add2);
				System.out.println("Landmark : "+land);
				System.out.println("City : "+city);
				System.out.println("State : "+state);
				System.out.println("ZIP Code : "+zip);
				Long slotid = slotRepository.getId(course,LocalDate.parse(date),timeIn,timeOut,address1,address2,landmark,zipCode,city,state);
				System.out.println("Slot Id : "+slotid);
				Slots slotInfo= slotService.decreaseCapacity(slotid);
				studentDto.slotDetails(slotInfo);
				studentService.save(studentDto);
				studentService.sendEmail(email,SlotDate,timeIn,timeOut);
				
				return "redirect:/slot_home?Success";
			
		}
		return "slot_home";
	}
	
	//Slot Main Controller
	
	@GetMapping("/slot_main")
	public String SlotHome2(Model model)
	{
		
		this.email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(email.equalsIgnoreCase("anonymousUser"))
		{
			return "redirect:/login";
		}
		
		
		
		return "slot_main"; 
	}
	@PostMapping("/slot_main/trainer")
	public String slot_index()
	{
		return "redirect:/slot_index";
	}
	@PostMapping("/slot_main/trainee")
	public String slot_home()
	{
		return "redirect:/slot_home";
	}
	
	
}