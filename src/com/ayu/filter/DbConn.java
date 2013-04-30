package com.ayu.filter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
