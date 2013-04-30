package com.ayu.filter;
/*
 * @author
 * Ayushman Dutta
 * email ayushman999@gmail.com
 * 
 */

public class Timer {
	long Start_time,interval,temp,End_time;int count;long mils;
	public Timer(long time)
	{
		this.Start_time=System.currentTimeMillis();
		this.temp = Start_time;
		this.count = 1;
		this.mils=time;
		
	}
	public boolean check(long End_time)
	{
		
		if((End_time-temp)<=mils)
		{	//System.out.println("Start time is"+Start_time);
			//System.out.println("Difference is"+(End_time-temp));
			return true;
		}
		else
		{
			return false;
		}
		
	}

}
