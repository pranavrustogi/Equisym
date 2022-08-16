package com.equisym.web.dto;

import java.sql.Date;
//import java.time.LocalDateTime;
//import java.util.Collection;

//import com.equisym.model.Role;

import net.bytebuddy.utility.RandomString;

public class UserRegistrationDto {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String ssn;
	private String country;
	private String state;
	private String pinCode;
	private String contact;
	private boolean enabled=false;
	private boolean adminVerified = false;
	private String verificationCode = RandomString.make(64);
	private String resetPasswordCode = RandomString.make(64);
	private Date registrationDate = new Date(System.currentTimeMillis());
	private String roleName;
	
	
	
	
	public UserRegistrationDto(String firstName, String lastName, String email, String password, String ssn, String country,
			String state, String pinCode, String contact, String roleName) 
	{
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.ssn = ssn;
		this.country = country;
		this.state = state;
		this.pinCode = pinCode;
		this.contact = contact;
		this.roleName=roleName;
	}
	public String getResetPasswordCode() {
		return resetPasswordCode;
	}
	public void setResetPasswordCode(String resetPasswordCode) {
		this.resetPasswordCode = resetPasswordCode;
	}
	public UserRegistrationDto() {
		// TODO Auto-generated constructor stub
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public boolean isEnabled() {
		return enabled;
	}
	
	public String getVerificationCode() 
	{
		return verificationCode;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public boolean isAdminVerified() {
		return adminVerified;
	}
	public void setAdminVerified(boolean adminVerified) {
		this.adminVerified = adminVerified;
	}
	
	
	

}
