package com.equisym.web.dto;


import java.time.DayOfWeek;
import java.time.LocalDate;

import javax.persistence.Column;

//import java.sql.Date;

//import java.sql.String;
//import java.time.String;
//import java.time.String;


import org.springframework.format.annotation.DateTimeFormat;

import com.equisym.model.Slots;
import com.equisym.model.Users;

public class StudentsDto 
{
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private static LocalDate date;
	private static DayOfWeek day; 
	private static String timeIn;
	private static String timeOut;
	private static String course;
	private static String address1;
	
	private static String address2;
	
	
	private static String landmark;
	private static String city;
	private static String state;
	
	private static String zipCode;
	
	
	private static String firstName;
	private static String lastName;
	private static String email;
	private static String contact;
	private static String roleName;
	private static String ssn;
	


	
	
	public StudentsDto()
	{}

	public static void userDetails(Users user)
	{
		firstName = user.getFirstName();
		lastName = user.getLastName();
		email = user.getEmail();
		contact = user.getContact();
		roleName = user.getRoleName();
		ssn = user.getSsn();
	}
	public void slotDetails(Slots slot)
	{
		course = slot.getCourse();
		date = slot.getDate();
		day = slot.getDay();
		timeIn = slot.getTimeIn();
		timeOut = slot.getTimeOut();
		address1= slot.getAddress1();
		address2 = slot.getAddress2();
		landmark = slot.getLandmark();
		zipCode = slot.getZipCode();
		city = slot.getCity();
		state= slot.getState();
	}

	public StudentsDto(LocalDate date, DayOfWeek day, String timeIn, String timeOut, String landmark, String zipCode, String course, double latitude, double longitude) {
		super();
		this.date = date;
		
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		this.course = course;
		this.day = day;
		
	
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	

	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public DayOfWeek getDay() 
	{
		return day;
	}

	public void setDay(DayOfWeek day) 
	{
		this.day = day;
	}
	
	



	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTimeIn() {
		return timeIn;
	}



	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}



	public String getTimeOut() {
		return timeOut;
	}



	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}




	public String getCourse() {
		return course;
	}



	public void setCourse(String course) {
		this.course = course;
	}

	

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public static String getAddress1() {
		return address1;
	}

	public static void setAddress1(String address1) {
		StudentsDto.address1 = address1;
	}

	public static String getAddress2() {
		return address2;
	}

	public static void setAddress2(String address2) {
		StudentsDto.address2 = address2;
	}

	public static String getLandmark() {
		return landmark;
	}

	public static void setLandmark(String landmark) {
		StudentsDto.landmark = landmark;
	}

	public static String getZipCode() {
		return zipCode;
	}

	public static void setZipCode(String zipCode) {
		StudentsDto.zipCode = zipCode;
	}

	public static String getCity() {
		return city;
	}

	public static void setCity(String city) {
		StudentsDto.city = city;
	}

	public static String getState() {
		return state;
	}

	public static void setState(String state) {
		StudentsDto.state = state;
	}

	
	
	
	
	

}
