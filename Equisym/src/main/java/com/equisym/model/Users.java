package com.equisym.model;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;

@Entity
@Table(name= "users" /*, uniqueConstraints = @UniqueConstraint(columnNames = {"EMAIL_ID","CONTACT"})*/)
public class Users 
{
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="EMAIL_ID")
	private String email;
	
	private String password;
	
	@Column(name="SSN_NO")
	private String ssn;
	@Column(name="COUNTRY")
	private String country;
	@Column(name="STATE")
	private String state;
	@Column(name="PIN_CODE")
	private String pinCode;
	@Column(name="CONTACT")
	private String contact;
	
	@Column(name="VERIFIED")
	private boolean enabled;
	
	@Column(name="ROLE")
	private String roleName;
	
	@Column(name="VERIFICATION_CODE")
	private String verificationCode;
	
	@Column(name="RESET_PASSWORD_CODE")
	private String resetPasswordCode;
	
	@Column(name="REGISTRATION_DATE")
	private Date registrationDate = new Date(System.currentTimeMillis());
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name="user_roles",
			joinColumns = @JoinColumn( 
					name="user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name="role_id", referencedColumnName = "id"))
	private Collection<Role> roles;
	
	
	
	
	public Users()
	{}
	
	public Users(String firstName, String lastName, String email, String password, String ssn, String country, String state, String pinCode, String contact, boolean enabled, String verificationCode,String resetPasswordCode,String roleName,Date registrationDate) 
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
		this.enabled = enabled;
		this.verificationCode=verificationCode;
		this.roleName=roleName;
		this.resetPasswordCode = resetPasswordCode;
		this.registrationDate=registrationDate;
	}

	
	public String getResetPasswordCode() {
		return resetPasswordCode;
	}

	public void setResetPasswordCode(String resetPasswordCode) {
		this.resetPasswordCode = resetPasswordCode;
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

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
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
	
	

}
