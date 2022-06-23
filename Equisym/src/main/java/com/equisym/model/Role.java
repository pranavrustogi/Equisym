package com.equisym.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Table(name= "role")
public class Role 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="ROLE_NAME")
	private String role_name;
	
	@Column(name="PROFILE")
	private String profile;
	

	
	public Role()
	{}



	public Role(String role_name, String profile) {
		super();
		this.role_name = role_name;
		this.profile = profile;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}


	public String getRole_name() {
		return role_name;
	}



	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}



	public String getProfile() {
		return profile;
	}



	public void setProfile(String profile) {
		this.profile = profile;
	}
	
}
