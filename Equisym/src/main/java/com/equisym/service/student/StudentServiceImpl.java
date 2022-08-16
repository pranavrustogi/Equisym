package com.equisym.service.student;

import java.io.UnsupportedEncodingException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.equisym.model.Slots;
import com.equisym.model.Students;
import com.equisym.model.Users;
import com.equisym.model.Repository.SlotRepository;
import com.equisym.model.Repository.StudentRepository;
import com.equisym.web.dto.StudentsDto;


@Service
public class StudentServiceImpl implements StudentService
{
	private StudentRepository studentRepository;
	@Autowired
	private JavaMailSender mailSender;

	
	public StudentServiceImpl(StudentRepository studentRepository)
	{
		super();
		this.studentRepository = studentRepository;
	}
	
	
	public Students save(StudentsDto studentsDto) 
	{
		Students students = new Students(studentsDto.getFirstName(),studentsDto.getLastName(),studentsDto.getEmail(),studentsDto.getContact(), studentsDto.getRoleName(), studentsDto.getCourse(),studentsDto.getDate(),studentsDto.getDate().getDayOfWeek(),studentsDto.getTimeIn(),studentsDto.getTimeOut(), studentsDto.getSsn(), studentsDto.getAddress1(), studentsDto.getAddress2(), studentsDto.getLandmark(), studentsDto.getZipCode(), studentsDto.getCity(), studentsDto.getState());
		return studentRepository.save(students);
	}
	
	@Override
	public void sendEmail(String email,LocalDate date, String timeIn, String timeOut) 
	{
		try 
		{
			Students student = studentRepository.getSlots(email,date,timeIn,timeOut);
			String subject = "Slot Details";
			String senderName = "Equisym Team";
			String mailContent= "<p>Dear " + student.getFirstName() +" " +student.getLastName() +",</p>";
			mailContent+= "<p>Congratulations Your Slot has been booked</p>";
			mailContent+= "<p>Below are the details:</p>";
			mailContent+="<p> "+"<b>Course</b> : "+ student.getCourse() + "</p>";
			mailContent+="<p> "+"<b>Date & Day</b> : "+ student.getDate() + "(" + student.getDate().getDayOfWeek() +")"+ "</p>";
			mailContent+="<p> "+"<b>Start Time</b> : "+ student.getTimeIn() + "</p>";
			mailContent+="<p> "+"<b>End Time</b> : "+ student.getTimeOut()+ "</p>";
			mailContent+="<p> "+"<b>Venue</b> : "+ student.getAddress1()+"<br>"+ student.getAddress2()+ "<br>"+ student.getLandmark()+"<br>"+ student.getCity()+"<br>"+ "</p>";
			mailContent+="<p> "+"<b>ZIP Code</b> : "+ student.getZipCode() + "</p>";
			
			mailContent+= "<p> Thank you<br>Equisym Team</p>";
		
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("equisymsantaknights@gmail.com", senderName);
			helper.setTo(student.getEmail());
			helper.setSubject(subject);
			
			helper.setText(mailContent, true);
			mailSender.send(message);
		
		}
		catch (MailException | UnsupportedEncodingException | MessagingException e) 
		{
		e.printStackTrace();
		}
	}
	
	
		
		
}

