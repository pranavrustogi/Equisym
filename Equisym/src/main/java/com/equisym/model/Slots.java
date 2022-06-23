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

@Table(name= "slots")
@Entity(name="slots")
public class Slots 
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
	@Column(name="ADDRESS1")
	private String address1;
	@Column(name="ADDRESS2")
	private String address2;
	
	@Column(name="LANDMARK")
	private String landmark;
	@Column(name="ZipCode")
	private String zipCode;
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
	
	private double latitude;
	private double longitude;
	@Column(name="Capacity")
	private int capacity; 
	
	@Column(name="END_TIME")
	private String timeOut;
	
	public Slots()
	{}

	public Slots(String firstName, String lastName, String email,String address1, String address2, String landmark,String zipCode ,String contact,String roleName ,String course, LocalDate date, 
			DayOfWeek day, String timeIn, String timeOut, double latitude, double longitude, int capacity) 
	{
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address1 = address1;
		this.address2 = address2;
		this.landmark = landmark;
		this.zipCode = zipCode;
		this.contact = contact;
		this.roleName=roleName;
		this.course = course;
		this.date = date;
		this.day = day;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		this.latitude=latitude;
		this.longitude=longitude;
		this.capacity = capacity;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
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
