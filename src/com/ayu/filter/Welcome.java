package com.ayu.filter;
/*
 * @author
 * Ayushman Dutta
 * email ayushman999@gmail.com
 * 
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);//calling doPost from doGet for added security
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String input=request.getParameter("input");
			out.println("<!DOCTYPE html/>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Application To Be Protected</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>Help Us To Be Safe</p>");
			out.println(input);
			out.println("</body>");
			out.println("</html>");
			
	}

}
