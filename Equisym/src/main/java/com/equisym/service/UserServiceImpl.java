package com.equisym.service;


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
//import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.equisym.model.Role;
import com.equisym.model.Slots;
import com.equisym.model.Users;
import com.equisym.model.Repository.UserRepository;
import com.equisym.web.dto.UserRegistrationDto;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService{
	
	private SecretKey key;
	private int KEY_SIZE = 128;
	private int T_LEN = 128;
	private Cipher encryptionCipher;
	private String public_key = "Equisym@Santa123";
	private String encryption_key;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncode;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	private String decryptedMessage;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncode= new BCryptPasswordEncoder();
	}

	@Override
	public Users save(UserRegistrationDto registrationDto) 
	{
		Users users = new Users(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(),passwordEncoder.encode(registrationDto.getPassword()), registrationDto.getSsn(), registrationDto.getCountry(), registrationDto.getState(), registrationDto.getPinCode(), registrationDto.getContact(),registrationDto.isEnabled(),registrationDto.getVerificationCode(),registrationDto.getResetPasswordCode(),registrationDto.getRoleName(),registrationDto.getRegistrationDate(), registrationDto.isAdminVerified()/*,Arrays.asList(new Role("",""))*/);
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

	
	public void adminAprrove(Long id) {
		Users user = null;
		Optional<Users> users = userRepository.findById(id);
		if(users.isPresent())
		{
			user = users.get();
		}
		user.setAdminVerified(true);
		userRepository.save(user);
		sendApproveMail(user);
		return;
		
	}
	public void adminDeny(Long id)
	{
		Users user = null;
		Optional<Users> users = userRepository.findById(id);
		if(users.isPresent())
		{
			user = users.get();
		}
		if(user.getRoleName().equalsIgnoreCase("Trainer"))
		{
			System.out.println("Delete Registration");
			userRepository.delete(user);
			sendDeleteMail(user);
		}
		else if(user.getRoleName().equalsIgnoreCase("Trainer & Trainee"))
		{
			System.out.println("Change Role Name");
			user.setRoleName("Trainee");
			userRepository.save(user);
			sendChangeMail(user);
		}
		
		return;
	}

	public void sendApproveMail(Users userInfo) 
	{
		try 
		{
			String subject = "Verification Successful";
			String senderName = "Equisym Team";
			String mailContent= "<p>Dear " + userInfo.getFirstName() +" " +userInfo.getLastName() +",</p>";
			mailContent+= "<p>Congratulations, Your Verification Process is Completed!!.</p>";
			mailContent+= "<p> Thank you<br>Equisym Team</p>";
		
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("equisymsantaknights@gmail.com", senderName);
			helper.setTo(userInfo.getEmail());
			helper.setSubject(subject);
			
			helper.setText(mailContent, true);
			mailSender.send(message);
		
		}
		catch (MailException | UnsupportedEncodingException | MessagingException e) 
		{
		e.printStackTrace();
		}
		
	}
	
	public void sendDeleteMail(Users userInfo) 
	{
		try 
		{
			String subject = "Registration Cancelled";
			String senderName = "Equisym Team";
			String mailContent= "<p>Dear " + userInfo.getFirstName() +" " +userInfo.getLastName() +",</p>";
			mailContent+= "<p>Sorry, Your Registration with Equisym as Trainer has been denied</p>";
			mailContent+= "<p>You can try again later. You can contact us to get further information</p>";
			mailContent+= "<p> Thank you<br>Equisym Team</p>";
		
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("equisymsantaknights@gmail.com", senderName);
			helper.setTo(userInfo.getEmail());
			helper.setSubject(subject);
			
			helper.setText(mailContent, true);
			mailSender.send(message);
		
		}
		catch (MailException | UnsupportedEncodingException | MessagingException e) 
		{
		e.printStackTrace();
		}
		
	}
	
	public void sendChangeMail(Users userInfo) 
	{
		try 
		{
			String subject = "Role Change";
			String senderName = "Equisym Team";
			String mailContent= "<p>Dear " + userInfo.getFirstName() +" " +userInfo.getLastName() +",</p>";
			mailContent+= "<p>Sorry, Your Registration with Equisym as Trainer and Trainee has been changed to Trainee Only.</p>";
			mailContent+= "<p>You can try again later. You can contact us to get further information</p>";
			mailContent+= "<p> Thank you<br>Equisym Team</p>";
		
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("equisymsantaknights@gmail.com", senderName);
			helper.setTo(userInfo.getEmail());
			helper.setSubject(subject);
			
			helper.setText(mailContent, true);
			mailSender.send(message);
		
		}
		catch (MailException | UnsupportedEncodingException | MessagingException e) 
		{
		e.printStackTrace();
		}
		
	}


	public void setAdminVerifiedTrue(String email) 
	{
		Users user = userRepository.findByEmail(email);
		user.setAdminVerified(true);
		System.out.println("Hello Trainee : "+email);
		userRepository.save(user);	
	}
	
	// Encryption & Decryption of data
	/*
	public void init() throws Exception
	{
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(KEY_SIZE);
		key = generator.generateKey();
	}
	
	public String encrypt(String message) throws Exception
	{
		byte[] messageInBytes = message.getBytes();
		encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
		encryptionCipher.init(Cipher.ENCRYPT_MODE,key);
		byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
		
		return encode(encryptedBytes);
				
	}
	
	public String decrypt(String encryptedMessage) throws Exception
	{
		byte[] messageInBytes = decode(encryptedMessage);
		Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
		GCMParameterSpec spec = new GCMParameterSpec(T_LEN,encryptionCipher.getIV());
		decryptionCipher.init(Cipher.DECRYPT_MODE,key,spec);
		byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
		return new String(decryptedBytes);
	}
	
	*/
	private String encode(byte[] data)
	{
		return Base64.getEncoder().encodeToString(data);
	}
	private byte[] decode(String data)
	{
		return Base64.getDecoder().decode(data);
	}

	/*
	public String convertMessage(String message) throws Exception
	{
			init();
			String encryptedMessage = encrypt(message);
			decryptedMessage = decrypt(encryptedMessage);
			System.out.println("Decrypted Message : "+decryptedMessage);
			return encryptedMessage;
	}
	public String convertMessageAgain(String encryptedMessage) throws Exception
	{
		init();
		String decryptedMessage = decrypt(encryptedMessage);
		return  decryptedMessage;
	}
	private String encryptSSN(String ssn)
	{
		String encodedSSN = this.passwordEncode.encode(ssn);
		
	}
	*/
	
	public String getKey()
	{
		byte[] array = new byte[16]; 
	    new Random().nextBytes(array);
	    String generatedString = new String(array, Charset.forName("UTF-8"));
	    return generatedString;
	}
	
	public String encryptKey(String keyy) throws Exception
	{
		byte[] k = public_key.getBytes();
		SecretKeySpec key =new SecretKeySpec(k,"AES");
		Cipher enc =Cipher.getInstance("AES"); 		
		enc.init(Cipher.ENCRYPT_MODE,key);
		byte[] encryptedBytes = enc.doFinal(decode(keyy));
		return encode(encryptedBytes);
	}
	public String decryptKey(String keyy) throws Exception
	{
		byte[] k = public_key.getBytes();
		SecretKeySpec key =new SecretKeySpec(k,"AES");
		Cipher enc =Cipher.getInstance("AES"); 		
		enc.init(Cipher.DECRYPT_MODE,key);
		byte[] decryptedBytes = enc.doFinal(decode(keyy));
		return encode(decryptedBytes);
	}
	
	public String encrypt(String message) throws Exception
	{
		byte[] k = encryption_key.getBytes();
		SecretKeySpec key =new SecretKeySpec(k, 0, k.length, "AES"); 
		Cipher enc =Cipher.getInstance("AES");
		enc.init(Cipher.ENCRYPT_MODE,key);
		byte[] encryptedBytes = enc.doFinal(decode(message));
		return encode(encryptedBytes);
	}
	public String decrypt(String message) throws Exception
	{
		byte[] k = encryption_key.getBytes();
		SecretKeySpec key =new SecretKeySpec(k, 0, k.length, "AES"); 
		Cipher enc =Cipher.getInstance("AES"); 		
		enc.init(Cipher.DECRYPT_MODE,key);
		byte[] decryptedBytes = enc.doFinal(decode(message));
		return encode(decryptedBytes);
	}
	public void setKey(String encryption_key)
	{
		this.encryption_key = encryption_key;
	}
	

	


	

}
