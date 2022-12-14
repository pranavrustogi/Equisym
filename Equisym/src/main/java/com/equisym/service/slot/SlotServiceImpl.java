package com.equisym.service.slot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.equisym.model.Slots;
import com.equisym.model.Users;
import com.equisym.model.Repository.SlotRepository;
import com.equisym.web.dto.SlotCreationDto;

@Service
public class SlotServiceImpl implements SlotService
{
	private SlotRepository slotRepository;

	
	public SlotServiceImpl(SlotRepository slotRepository)
	{
		super();
		this.slotRepository = slotRepository;
	}
	
	
	public Slots save(SlotCreationDto creationDto) 
	{
		Slots slots = new Slots(creationDto.getFirstName(),creationDto.getLastName(),creationDto.getEmail(),creationDto.getAddress1(),creationDto.getAddress2(),creationDto.getLandmark(),creationDto.getZipCode(),creationDto.getCity() ,creationDto.getState() ,creationDto.getContact(), creationDto.getRoleName(), creationDto.getCourse(),creationDto.getDate(),creationDto.getDate().getDayOfWeek(),creationDto.getTimeIn(),creationDto.getTimeOut(), creationDto.getLatitude(), creationDto.getLongitude(), creationDto.getCapacity());
		return slotRepository.save(slots);
	}
	
	
	public void updateSlot(Long id, LocalDate date, DayOfWeek day  ,String timeIn, String timeOut,String address1, String address2, String landmark, String zipCode, String city, String state, double latitude, double longitude, int capacity)
	{
		Slots slot = null;
		Optional<Slots> slots = slotRepository.findById(id);
		if(slots.isPresent())
		{
			slot = slots.get();
			
			slot.setTimeIn(timeIn);
			slot.setTimeOut(timeOut);
			slot.setAddress1(address1);
			slot.setAddress2(address2);
			slot.setLandmark(landmark);
			slot.setZipCode(zipCode);
			slot.setDate(date);
			slot.setDay(day);
			slot.setLatitude(latitude);
			slot.setLongitude(longitude);
			slot.setCapacity(capacity);
			slot.setLandmark(landmark);
			slot.setZipCode(zipCode);
			slot.setCity(city);
			slot.setState(state);
			
			slotRepository.save(slot);
			return;
			
		}
		
		
		
	}
	
	public Slots decreaseCapacity(Long id)
	{
		Slots slot = null;
		Optional<Slots> slots = slotRepository.findById(id);
		if(slots.isPresent())
		{
			slot = slots.get();
		}
		int capacity = slot.getCapacity();
		capacity = capacity-1;
		slot.setCapacity(capacity);
		slotRepository.save(slot);
		return slot;
	}


	public String getAddress2(Slots slots) {
		
		return slots.getAddress2();
	}



	public String getAddress1(Slots slots) {
		// TODO Auto-generated method stub
		return slots.getAddress1();
	}


	public String getLandmark(Slots slots) {
		// TODO Auto-generated method stub
		return slots.getLandmark();
	}
	public String getCity(Slots slots) {
		// TODO Auto-generated method stub
		return slots.getCity();
	}

	public String getState(Slots slots) {
		// TODO Auto-generated method stub
		return slots.getState();
	}

	
	public String getZipCode(Slots slots) {
		// TODO Auto-generated method stub
		return slots.getZipCode();
	}


	
	public double getLatitude(Slots slots) {
		// TODO Auto-generated method stub
		return slots.getLatitude();
	}


	
	public double getLongitude(Slots slots) {
		// TODO Auto-generated method stub
		return slots.getLongitude();
	}


	
	public List<String> getDifferentCourses(List<String> l1, List<String> l2) {
		List<String> uncommon = new ArrayList<String>();
		for(int j=0;j<l1.size();j++)
		{
			if(!l2.contains(l1.get(j)))
				uncommon.add(l1.get(j));
		}
		for(int j=0;j<l2.size();j++)
		{
			if(!l1.contains(l2.get(j)))
				uncommon.add(l2.get(j));
		}
		return uncommon;
	}


	
	public boolean checkOverLap(String timeIn, String timeOut, String start, String end) 
	{
		int time1 = getMinute(timeIn, Locale.ROOT);
		int time2 = getMinute(timeOut, Locale.ROOT);
		int time3 = getMinute(start, Locale.ROOT);
		int time4 = getMinute(end, Locale.ROOT);
		
		return checkOverLapHelper(time1,time2,time3,time4);
	}
	private static boolean checkOverLapHelper(int timeIn, int timeOut, int start, int end) 
	{
		if((start<=timeIn) && (end>=timeIn))
			return true;
		else if((start<=timeOut) && (end>=timeOut))
			return true;
		else if(start>=timeIn && start<=timeOut)
			return true;
		else if(end>=timeIn && end<=timeOut)
			return true;
			
		else
			return false;
	}
	
	public static int getMinute(String timeString, Locale locale) 
	{
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH[:][.]mm", locale);
		  LocalTime time = LocalTime.parse(timeString, formatter);
		  return time.getHour()*60 + time.getMinute();
	}


	
}
