package com.equisym.service.slot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

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
void updateSlot(Long id, LocalDate date, DayOfWeek day  ,String timeIn, String timeOut,String address1, String address2, String landmark, String zipCode, String city, String state, double latitude, double longitude, int capacity);
Slots decreaseCapacity(Long id);
String getAddress2(Slots slots);
String getAddress1(Slots slots);
String getLandmark(Slots slots);
String getCity(Slots slots);
String getState(Slots slots);
String getZipCode(Slots slots);
double getLatitude(Slots slots);
double getLongitude(Slots slots);
List<String> getDifferentCourses(List<String> trainerCourses, List<String> allCourses);
boolean checkOverLap(String timeIn, String timeOut, String start, String end);

}
