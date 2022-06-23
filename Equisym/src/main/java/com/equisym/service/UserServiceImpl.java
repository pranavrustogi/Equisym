package com.equisym.service;


import java.io.UnsupportedEncodingException;
//import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.equisym.model.Role;
import com.equisym.model.Users;
import com.equisym.model.Repository.UserRepository;
import com.equisym.web.dto.UserRegistrationDto;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService{
	
	
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public Users save(UserRegistrationDto registrationDto) 
	{
		Users users = new Users(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(),passwordEncoder.encode(registrationDto.getPassword()), registrationDto.getSsn(), registrationDto.getCountry(), registrationDto.getState(), registrationDto.getPinCode(), registrationDto.getContact(),registrationDto.isEnabled(),registrationDto.getVerificationCode(),registrationDto.getResetPasswordCode(),registrationDto.getRoleName(),registrationDto.getRegistrationDate() /*,Arrays.asList(new Role("",""))*/);
		return userRepository.save(users);
	}
	
	@Override
	public void sendVerificationMail(UserRegistrationDto registrationDto, String siteURL) throws UnsupportedEncodingException, MessagingException, MailException
	{
		try 
		{
			String subject = "Please verify your registration";
			String senderName = "Equisym Team";
			String mailContent= "<p>Dear " + registrationDto.getFirstName() +" " +registrationDto.getLastName() +",</p>";
			mailContent+= "<p>Please Click the link below to verify your registration:</p>";
			
			String verifyURL = siteURL + "/verify?code="+ registrationDto.getVerificationCode();
			
			mailContent+= "<h3><a href=\"" + verifyURL +"\">VERIFY</a></h3>";
			
			mailContent+= "<p> Thank you<br>Equisym Team</p>";
		
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("equisymsantaknights@gmail.com", senderName);
			helper.setTo(registrationDto.getEmail());
			helper.setSubject(subject);
			
			helper.setText(mailContent, true);
			mailSender.send(message);
		
		}
		catch (MailException | UnsupportedEncodingException | MessagingException e) 
		{
		
		e.printStackTrace();
		}
	}
	
	@Override
	public void sendResetPasswordMail(Users user, String siteURL) throws UnsupportedEncodingException, MessagingException, MailException
	{
		try 
		{
			Users userInfo = userRepository.findByEmail(user.getEmail());
			String subject = "Reset Password";
			String senderName = "Equisym Team";
			String mailContent= "<p>Dear " + userInfo.getFirstName() +" " +userInfo.getLastName() +",</p>";
			mailContent+= "<p>Please Click the link below to reset your password:</p>";
			
			String resetURL = siteURL + "/reset?code="+ userInfo.getResetPasswordCode();
			
			mailContent+= "<h3><a href=\"" + resetURL +"\">RESET PASSWORD</a></h3>";
			
			mailContent+= "<p> Thank you<br>Equisym Team</p>";
		
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("equisymsantaknights@gmail.com", senderName);
			helper.setTo(user.getEmail());
			helper.setSubject(subject);
			
			helper.setText(mailContent, true);
			mailSender.send(message);
		
		}
		catch (MailException | UnsupportedEncodingException | MessagingException e) 
		{
		e.printStackTrace();
		}
	}
	
	
	
	@Override
	public boolean verify(UserRegistrationDto registrationDto, String verificationCode)
	{
		Users user = userRepository.findByVerificationCode(verificationCode);
		if(user==null || user.isEnabled())
		{
			return false;
		}
		else
		{
			registrationDto.setEnabled(true);
			user.setEnabled(registrationDto.isEnabled());
			userRepository.save(user);
			return true;
		}
	}
	@Override
	public void updatePassword(Users user, String newPassword)
	{
		
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(newPassword);
			user.setPassword(encodedPassword);
			user.setResetPasswordCode(RandomString.make(64));
			userRepository.save(user);
	}
	
	
	public Users getCode(String Code)
	{
		Users user = userRepository.findByVerificationCode(Code);
		return user;
	}
	public Users getResetPasswordCode(String Code)
	{
		Users user = userRepository.findByResetPasswordCode(Code);
		return user;
	}
	public Users checkEmail(String email)
	{
		Users user = userRepository.findByEmail(email);
		return user;
	}
	public Users checkContact(String contact)
	{
		Users user = userRepository.findByContact(contact);
		return user;
	}
	public boolean checkVerified(String username)
	{
		Users users = userRepository.findByEmail(username);
		if(users.isEnabled())
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Users user = userRepository.findByEmail(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles)
	{
		 return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole_name())).collect(Collectors.toList());
	}


	

}
