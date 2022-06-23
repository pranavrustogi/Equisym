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

@Repository
public interface SlotRepository extends JpaRepository<Slots,Long>
{
	//Slots findByEmail(String email);
	//Slots findByDay(String day);
	Optional<Slots> findById(Long id);
	
	//@Query("SELECT s FROM slots s WHERE s.email=?1 and s.day = ?2 ORDER BY s.id ASC")
	//List<Slots> getSlotsByEmail(String email, LocalDate date);
	
	@Query("SELECT s FROM slots s WHERE s.email=?1 ORDER BY s.id ASC")
	List<Slots> getSlots(String email);
	List<Slots> findByEmail(String email);
	
	@Query("SELECT s FROM slots s ORDER BY s.id ASC")
	List<Slots> getSlots();
	
	@Query("SELECT DISTINCT(course) FROM slots WHERE capacity>0")
	List<String> getCourses();
	
	@Query("SELECT DISTINCT(s.date) FROM slots s WHERE s.course = ?1 and s.capacity>0")
	List<LocalDate> getSlotDate(String course);
	@Query("SELECT s FROM slots s WHERE s.course = ?1 and s.date = ?2 and s.capacity>0")
	List<Slots> getSlotTime(String course,LocalDate date);
	@Query("SELECT s.timeIn FROM slots s WHERE s.id = ?1")
	String getTimeInById(Long id);
	@Query("SELECT s.timeOut FROM slots s WHERE s.id = ?1")
	String getTimeOutById(Long id);
	@Query("SELECT s FROM slots s WHERE s.course = ?1 and s.date = ?2 and s.timeIn = ?3 and s.timeOut = ?4 and s.capacity>0")
	List<Slots> getAvailableSlots(String SlotCourse,LocalDate SlotDate,String timeIn,String timeOut);

	@Query("SELECT id FROM slots WHERE course=?1 and date = ?2 and timeIn = ?3 and timeOut = ?4 and address1 = ?5 and address2 = ?6 and landmark = ?7 and zipCode = ?8")
	Long getId(String slotCourse, LocalDate slotDate, String timeIn, String timeOut, String address1, String address2,
			String landmark, String zipCode);

	
	

	

	
	
	
}
