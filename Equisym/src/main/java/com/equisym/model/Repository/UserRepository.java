package com.equisym.model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import com.equisym.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Long>
{
 Users findByEmail(String email);
 
 Users findByVerificationCode(String verificationCode);
 
 Users findByResetPasswordCode(String resetPasswordCode);

 Users findByContact(String contact); 
 

 

 
 
}
