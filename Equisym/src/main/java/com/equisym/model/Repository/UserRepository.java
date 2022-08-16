package com.equisym.model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.equisym.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Long>
{
 Users findByEmail(String email);
 
 Users findByVerificationCode(String verificationCode);
 
 Users findByResetPasswordCode(String resetPasswordCode);

 Users findByContact(String contact);

 @Query("SELECT u from Users u where u.roleName= ?1 and u.adminVerified = ?2 ORDER BY u.id")
List<Users> findByAdminVerified(String role, boolean admin_verified);
 @Query("SELECT u from Users u where u.roleName= ?1 ORDER BY u.id")
List<Users> findByAdminVerified(String rolename); 
 

 

 
 
}
