package com.ayu.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import com.ayu.filter.RegularService;
/*
 * @author
 * Ayushman Dutta
 * email ayushman999@gmail.com
 * 
 */

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CheckFilter implements Filter {
	SimpleCache lruCache;
	long time;
	Timer t,t1;
	ClassPathXmlApplicationContext appContext;
	RegularService regService ;
	int timer;
	DbConn db;
	//TestService call;
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			String str = req.getRemoteAddr();
			if(lruCache.map.isEmpty())
			{
				//System.out.println("It is empty");
				lruCache.map.put(str, new Timer(db.time));
				chain.doFilter(request, response);
			}
			if(lruCache.map.containsKey(str))
			{
				t=lruCache.map.get(str);
				t.count++;
				lruCache.map.put(str, t);
			}
			else
			{
				lruCache.map.put(str,new Timer(db.time));
			}
			/**
			for (Map.Entry<String,Timer> e : lruCache.getAll()){
				if(e.getKey().equals(str))
				{	
					t = e.getValue();
					e.getValue().count++;
					lruCache.map.put(str,t);
					
					
				}
				else
				{
					if(lruCache.map.containsKey(str))
					{
						t1 = lruCache.map.get(str);
						t1.count++;
						lruCache.map.put(str,t1);
					}
					else{
					//lruCache.map.remove(e.getKey());
					lruCache.map.put(str,new Timer());
					}
				}
				
			}**/
			 if(t.count>timer)
			{	
				time = System.currentTimeMillis();
				//System.out.println(t.check(time)+"1");	
				if(t.check(time)==true)
				{	//System.out.println("OK");
					if(req.getServletContext().getAttribute(str)==null)
					{
				
					req.getServletContext().setAttribute(str, str);
					regService.registerUser(str,new Date().toString());
					lruCache.map.put(str,new Timer(db.time));
					//System.out.println((String) req.getServletContext().getAttribute("IP"));
					//System.out.println(t.check(time)+"2");	
					res.sendError(HttpServletResponse.SC_FORBIDDEN, "You Are Perceived as a Threat");
					}
					else
					{
						/*regService.registerUser(str,new Date().toString());
						lruCache.map.put(str,new Timer(db.time));//This will add to database even if it attacked even once.. So not reqd to save resources as attacker is in DB */
						chain.doFilter(request, response);
					}
				}
				else if(t.check(time)==false)
				{
					//System.out.println("Not Ok");
					//System.out.println(t.check(time)+"3");	
					lruCache.map.put(str,new Timer(db.time));
					//System.out.println(new Timer().count);
					chain.doFilter(request, response);
				}
				
			}
			else
			{
				//System.out.println(t.check(time)+"4");	
				// pass the request along the filter chain
				chain.doFilter(request, response);
			}
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		try {
			db = new DbConn();
			db.getData();
			timer=db.count;
			//System.out.println(db.time+ " "+db.count );
			lruCache = new SimpleCache(100);
			appContext = new ClassPathXmlApplicationContext(new String[] {"spring.xml"});
			regService = (RegularService) appContext.getBean("regularService");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
