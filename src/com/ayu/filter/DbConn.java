package com.ayu.filter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class DbConn {
	private Connection con;
	private Statement st;
	private ResultSet rs;
	long time;int count;String ip_range;
	public DbConn(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.print("Error  "+e);
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
			st = con.createStatement();
		} catch (SQLException e) {
			System.out.print("Error  "+e);
		}
		
	}
	public void getData()
	{
		String query = "select * from initials";
		try {
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			System.out.print("Error  "+e);
		}
		
			try {
				while(rs.next())
				{
					time=rs.getLong("time");
					count=rs.getInt("count");
					ip_range=rs.getString("iprange");
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	

}
