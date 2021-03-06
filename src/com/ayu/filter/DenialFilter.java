package com.ayu.filter;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * @Author
 * Ayushman Dutta
 * Email ayushman999@gmail.com
 * CopyRight Ayushman Dutta,2013
 *  This file is part of CloudIDS.
    CloudIDS is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    CloudIDS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CloudIDS.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

public class DenialFilter implements Filter {

  FilterConfig config;

  public String IP_RANGE;
  public String block_range;
  public DenialFilter() {
  }

  public void init(FilterConfig filterConfig) throws ServletException {

    this.config = filterConfig;
    DbConn db1 = new DbConn();
    db1.getData();
    block_range = db1.ip_range;
  }

  public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
	HttpServletRequest req = (HttpServletRequest) request;
	HttpServletResponse httpResp = null;
	  if (response instanceof HttpServletResponse)
	      httpResp = (HttpServletResponse) response;

	  String ip = req.getHeader("X-FORWARDED-FOR");  
	   if (ip == null) {  
		   ip = req.getRemoteAddr();  
	   }
    
    StringTokenizer toke = new StringTokenizer(ip, ".");
    int dots = 0;
    String byte1 = "";
    String byte2 = "";
    String client ="";

    while (toke.hasMoreTokens()) {

      ++dots;

      //if we've reached the second dot, break and check out the indx
      // value
      if (dots == 1) {

        byte1 = toke.nextToken();

      } else {

        byte2 = toke.nextToken();
        break;
      }
    }//while

    //Piece together half of the client IP address so it can be compared
    // with
    //the forbidden range represented by IPFilter.IP_RANGE
     client= byte1 + "." + byte2;
    /* if (block_range.equals(client)) {

         httpResp.sendError(HttpServletResponse.SC_FORBIDDEN,
             "Your IP address Range is blocked by the Admin!");

       } else {

         chain.doFilter(request, response);
       }
    */
    //todo here
	this.IP_RANGE = (String) req.getServletContext().getAttribute(ip);
   
    if(IP_RANGE!=null){
    if (IP_RANGE.equals(ip) || block_range.equals(client)) {
    	
      httpResp.sendError(HttpServletResponse.SC_FORBIDDEN,"You are in our DataBase as an attacker and blocked as a result.To be removed please mail your reason to ayushman999@gmail.com ");

    } else {
      chain.doFilter(request, response);
    }
    }
    else
    {
    	if (block_range.equals(client)) {
        	
    	      httpResp.sendError(HttpServletResponse.SC_FORBIDDEN,"You are in our DataBase as an attacker and blocked as a result.To be removed please mail your reason to ayushman999@gmail.com ");

    	    }
    	else{
    	chain.doFilter(request, response);
    }
  }
  }// doFilter

  public void destroy() {
    
  }

}