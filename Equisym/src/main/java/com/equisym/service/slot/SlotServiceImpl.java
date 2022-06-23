package com.equisym.service.slot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
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
		Slots slots = new Slots(creationDto.getFirstName(),creationDto.getLastName(),creationDto.getEmail(),creationDto.getAddress1(),creationDto.getAddress2(),creationDto.getLandmark(),creationDto.getZipCode(),creationDto.getContact(), creationDto.getRoleName(), creationDto.getCourse(),creationDto.getDate(),creationDto.getDate().getDayOfWeek(),creationDto.getTimeIn(),creationDto.getTimeOut(), creationDto.getLatitude(), creationDto.getLongitude(), creationDto.getCapacity());
		return slotRepository.save(slots);
	}
	
	
	public void updateSlot(Long id, LocalDate date, DayOfWeek day  ,String timeIn, String timeOut,String address1, String address2, String landmark, String zipCode, double latitude, double longitude, int capacity)
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
}