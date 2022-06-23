package com.equisym.service.slot;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.equisym.model.Slots;
import com.equisym.model.Users;
import com.equisym.web.dto.SlotCreationDto;

public interface SlotService 
{
Slots save(SlotCreationDto creationDto);
//Slots checkEmail(String email);
//Slots checkDay(String day);
//boolean hasSlot(String email , LocalDate date);
void updateSlot(Long id, LocalDate date, DayOfWeek day  ,String timeIn, String timeOut,String address1, String address2, String landmark, String zipCode, double latitude, double longitude, int capacity);
Slots decreaseCapacity(Long id);
}
