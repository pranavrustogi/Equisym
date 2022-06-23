package com.equisym.service.student;

import java.util.List;
import java.util.Map;
import com.equisym.model.Repository.SlotRepository;




public class AjaxDao 
{
	private SlotRepository slotRepository;
	
	public Map<Integer,String> getCourseMap()
	{
		
		Map<Integer,String> courseMap = null;
		List<String> courses = slotRepository.getCourses();
		for(int i=1;i<courses.size();i++)
		{
			courseMap.put(i,courses.get(i));
			i++;
		}
		return courseMap;
		
	}
	public Map<Integer,List<String>> getSlotDateMap()
	{
		List<String> courses = slotRepository.getCourses();
		Map<Integer,List<String>> slotDates = null;
		for(int i=1;i<courses.size();i++)
		{
			//slotDates.put(i, slotRepository.getSlotDate(courses.get(i-1)));
		}
		return slotDates;
	}
	
	
	
}
