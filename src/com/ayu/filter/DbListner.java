package com.ayu.filter;

import java.net.UnknownHostException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

/**
 * Application Lifecycle Listener implementation class DbListner
 *
 */
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
@WebListener
public class DbListner implements ServletContextListener {


	/**
     * Default constructor. 
     */
    public DbListner() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	MongoClient mongoClient = null;
    	 try {
    			mongoClient = new MongoClient();
    		} catch (UnknownHostException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    	 DB db = mongoClient.getDB( "test" );
    	 DBCollection coll;
    	 coll = db.getCollection("test");
    	 DBCursor cursor = coll.find();
    	 while(cursor.hasNext())
    	 {
    		 String ip = (String)cursor.next().get("Ip-Address");
    		 if(arg0.getServletContext().getAttribute(ip)==null)
    		 {
    			 arg0.getServletContext().setAttribute(ip,ip);
    		 }
    	 }
    	
    }

	
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
