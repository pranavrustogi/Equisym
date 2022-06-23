package com.equisym.model.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.equisym.model.Slots;
//import com.equisym.model.Users;
import com.equisym.model.Students;

@Repository
public interface StudentRepository extends JpaRepository<Students,Long>
{

@Query("SELECT s FROM students s WHERE s.email =?1 and s.date = ?2 and s.timeIn>= ?3 and s.timeOut<=?4")
List<Students> getSlots(String email, LocalDate date, String timeIn, String timeOut);
	
Students findByEmail(String email);

	
	
	
}
