package com.equisym.service;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.mail.MessagingException;

//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.equisym.model.Users;
import com.equisym.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService
{
	Users save(UserRegistrationDto registrationDto);
	boolean verify(UserRegistrationDto registrationDto,String verificationCode);
	void sendVerificationMail(UserRegistrationDto registrationDto, String siteURL) throws UnsupportedEncodingException, MessagingException;
	void sendResetPasswordMail(Users user, String siteURL) throws UnsupportedEncodingException, MessagingException;
	void updatePassword(Users user, String password);
	Users getCode(String code);
	Users getResetPasswordCode(String code);
	Users checkEmail(String email);
	Users checkContact(String contact);
	boolean checkVerified(String username);
	void adminAprrove(Long id);
	void adminDeny(Long id);
	void sendApproveMail(Users user);
	void sendDeleteMail(Users user);
	void sendChangeMail(Users user);

	void setAdminVerifiedTrue(String email);
	//String convertMessage(String parameter) throws Exception;
	//String convertMessageAgain(String parameter) throws Exception;
	String getKey() throws Exception;
	String encryptKey(String key) throws Exception;
	String decryptKey(String key) throws Exception;
	String encrypt(String message) throws Exception;
	String decrypt(String encryptedText) throws Exception;
	void setKey(String string);
	
}
