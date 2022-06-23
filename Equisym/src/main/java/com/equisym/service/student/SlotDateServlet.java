package com.equisym.service.student;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/SlotDateServlet")
public class SlotDateServlet extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		AjaxDao dao = new AjaxDao();
		Map<Integer,List<String>> slotDateMap = dao.getSlotDateMap();
		Gson json = new Gson();
		String slotDateString = json.toJson(slotDateMap);
		response.setContentType("text/html");
		response.getWriter().write(slotDateString);
		System.out.println("Ajax");
	}
}
