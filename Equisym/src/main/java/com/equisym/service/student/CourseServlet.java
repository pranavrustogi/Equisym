package com.equisym.service.student;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/CourseServlet")
public class CourseServlet extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Hey I am Ajax");
		AjaxDao dao = new AjaxDao();
		Map<Integer,String> courseMap = dao.getCourseMap();
		Gson json = new Gson();
		String coursesString = json.toJson(courseMap);
		response.setContentType("text/html");
		response.getWriter().write(coursesString);
		System.out.println("Hey I am Ajax");
	}
}
