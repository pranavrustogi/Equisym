package com.equisym.model;


import java.time.DayOfWeek;
import java.time.LocalDate;

//import java.sql.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import java.time.String;

//import java.time.String;
import org.springframework.format.annotation.DateTimeFormat;

@Table(name= "students")
@Entity(name="students")
public class Students 
{
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="CONTACT")
	private String contact;
	@Column(name="ROLE")
	private String roleName;
	@Column(name="COURSE")
	private String course;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="SLOT_DATE")
	private LocalDate date;
	
	private DayOfWeek day;
	@Column(name="START_TIME")
	private String timeIn;
	
	@Column(name="END_TIME")
	private String timeOut;
	@Column(name="SSN_NO")
	private String ssn;
	@Column(name="SLOT_ADDRESS1")
	private String address1;
	@Column(name="SLOT_ADDRESS2")
	private String address2;
	
	
	@Column(name="SLOT_LANDMARK")
	private String landmark;
	
	@Column(name="SLOT_CITY")
	private String city;
	@Column(name="SLOT_STATE")
	private String state;
	@Column(name="SLOT_ZIPCODE")
	private String zipCode;
	
	public Students()
	{}

	public Students(String firstName, String lastName, String email,String contact,String roleName ,String course, LocalDate date, 
			DayOfWeek day, String timeIn, String timeOut, String ssn, String address1, String address2, String landmark, String zipCode, String city, String state) 
	{
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	
		this.contact = contact;
		this.roleName=roleName;
		this.course = course;
		this.date = date;
		this.day = day;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		this.ssn= ssn;
		this.address1 = address1;
		this.address2 = address2;
		this.landmark = landmark;
		this.zipCode = zipCode;
		this.city= city;
		this.state= state;
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
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

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
	
	
	
}
