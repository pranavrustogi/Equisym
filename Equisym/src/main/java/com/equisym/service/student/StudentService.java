package com.equisym.service.student;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.equisym.model.Slots;
import com.equisym.model.Students;
import com.equisym.model.Users;
import com.equisym.web.dto.SlotCreationDto;
import com.equisym.web.dto.StudentsDto;

public interface StudentService 
{
	Students save(StudentsDto studentDto);
	
	void sendEmail(String email);
}
