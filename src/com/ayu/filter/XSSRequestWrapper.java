package com.ayu.filter;

import java.util.Date;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncodingException;
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
 
public class XSSRequestWrapper extends HttpServletRequestWrapper {
 
    private static Pattern[] patterns = new Pattern[]{
        // Script fragments
        Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
        //Script fragments
        Pattern.compile("\">|<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
        // src='...'
        Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // lonely script tags
        Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // eval(...)
        Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // expression(...)
        Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // javascript:...
        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
        // vbscript:...
        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
        // onload(...)=...
        Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        //onfocus(...)=...
        Pattern.compile("\"|onfocus=[a-z]*(.*?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        //SQL Injection
        Pattern.compile("\\w*((\\%27)|(\'))((\\%6F)|o|(\\%4F))((\\%72)|r|(\\%52))",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
        //Another SQL Injection Patter for Meta Characters
        Pattern.compile("((\\%3D)|(=))[^\n]*((\\%27)|(\')|(\\-\\-)|(\\%3B)|(;))",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
        //SQL Pattern Attack for MS Sql Attack
        Pattern.compile("exec(\\s|\\+)+(s|x)p\\w+",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
        //Sql Attack using Union keyword
        Pattern.compile("((\\%27)|(\'))union",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
      //Sql Attack using Update keyword
        Pattern.compile("((\\%27)|(\'))update",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
      //Sql Attack using Insert keyword
        Pattern.compile("((\\%27)|(\'))insert",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
      //Sql Attack using Delete keyword
        Pattern.compile("((\\%27)|(\'))delete",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
      //Sql Attack using Drop keyword
        Pattern.compile("((\\%27)|(\'))drop",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
        //Sql attack
        Pattern.compile("(\\%27)|(\')|(\\-\\-)|(\\%23)|(#)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE)
      
    };
 
    public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }
 
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
 
        if (values == null) {
            return null;
        }
 
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }
 
        return encodedValues;
    }
 
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return stripXSS(value);
    }
 
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value);
    }
 
    private String stripXSS(String value) {
    	String check;
    	 String str = super.getHeader("X-FORWARDED-FOR");  
    	   if (str == null) {  
    		   str = super.getRemoteAddr();  
    	   }
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library to
            // avoid encoded attacks.
        	check=value;
            try {
				value = ESAPI.encoder().canonicalize(value);
			} catch (EncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
            // Avoid null characters
            value = value.replaceAll("\0", "");
            // Remove all sections that match a pattern
            for (Pattern scriptPattern : patterns){
                value = scriptPattern.matcher(value).replaceAll("");
            }
            if(value.equals(check))
			{
				//No Need to Do Anything
			}
			else
			{	if(value.equals("W/396-1367338015000"))
			{
				//System.out.println("hellaluya");
			}
			else
			{	
				Object o =super.getServletContext().getAttribute("regService");
				RegularService r = (RegularService) o;
				r.registerUser(str,new Date().toString(),"Injection Attacks","test1");
				r.camCall(str);
				r.sendSSLMail("An Attack Has Occured.Please Check your System for Injection based attacks", "clouddefenceids@gmail.com");
				//System.out.println("Changed3");
				//System.out.println("value is->"+value+" "+"check is->"+check);
			}
        }
      }
        return value;
    }
}
