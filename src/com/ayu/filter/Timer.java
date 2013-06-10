package com.ayu.filter;
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
