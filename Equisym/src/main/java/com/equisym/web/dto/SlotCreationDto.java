package com.equisym.web.dto;


import java.time.DayOfWeek;
import java.time.LocalDate;

//import java.sql.Date;

//import java.sql.String;
//import java.time.String;
//import java.time.String;


import org.springframework.format.annotation.DateTimeFormat;

import com.equisym.model.Users;

public class SlotCreationDto 
{
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	private DayOfWeek day;
	private String timeIn;
	private String timeOut;
	private String address1;
	private String address2;
	private String landmark;
	private String zipCode;
	private String city;
	private String state;
	private String course;
	private double latitude;
	private double longitude;
	private int capacity;
	
	
	private static String firstName;
	private static String lastName;
	private static String email;
	private static String contact;
	private static String roleName;
	private static String ssn;


	
	
	public SlotCreationDto()
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

	public SlotCreationDto(LocalDate date, DayOfWeek day, String timeIn, String timeOut,String address1, String address2, String landmark, String zipCode, String city, String state, String course, double latitude, double longitude, int capacity) {
		super();
		this.date = date;
		this.day = day;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		this.address1 = address1;
		this.address2 = address2;
		this.zipCode = zipCode;
		this.landmark = landmark;
		this.city= city;
		this.state= state;
		this.course = course;
		this.latitude=latitude;
		this.longitude=longitude;
		this.capacity = capacity;
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


	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCourse() {
		return course;
	}



	public void setCourse(String course) {
		this.course = course;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public static String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	
	
	

}
