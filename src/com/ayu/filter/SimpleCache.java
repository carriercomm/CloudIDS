package com.ayu.filter;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;
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

public class SimpleCache {
 int num ;
 String str;Timer str1;
 LinkedHashMap<String, Timer> map;

 public SimpleCache(final int num) throws IOException{
 try{
 //this.str = str;
 //this.str1 = str1;
 this.num = num;
 }
 catch(NumberFormatException ne){
 System.exit(0);
 }
 map = new LinkedHashMap<String,Timer>() {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public boolean removeEldestEntry (Map.Entry<String,Timer> eldest){
 return size() > num;
 }
 };
 //map.put (str, str1);
 }

 public synchronized ArrayList<Entry<String, Timer>> getAll() {
 return new ArrayList<Map.Entry<String,Timer>>(map.entrySet());
 }
}
