package com.chekn.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
*/

public class OracleWorkDb 
{
	private static Connection conn=null;
	
	public static Connection getConnection()
	{
		new oracle.jdbc.driver.OracleDriver();       //加载驱动
		String url="jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String name="chekn";
		String pwd="chekncom";

		//Class.forName("com.mysql.jdbc.Driver"); //加载JDBC驱动
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();    //需要处理异常
		}catch(Exception e)
		{
			System.err.println("JDBC驱动加载出现问题");
			e.printStackTrace();
		}
		
		try
		{
			conn=DriverManager.getConnection(url,name,pwd);}
		catch(SQLException e)
		{
			System.err.println("连接错误");
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Connection getConnectionToServer()
	{
		new oracle.jdbc.driver.OracleDriver();       //加载驱动
		String url="jdbc:oracle:thin:@192.168.1.200:1521:orcl";
		String name="scierp";
		String pwd="seechangecom";

		//Class.forName("com.mysql.jdbc.Driver"); //加载JDBC驱动
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();    //需要处理异常
		}catch(Exception e)
		{
			System.err.println("JDBC驱动加载出现问题");
			e.printStackTrace();
		}
		
		try
		{
			conn=DriverManager.getConnection(url,name,pwd);}
		catch(SQLException e)
		{
			System.err.println("连接错误");
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection()    //关闭连接
	{
		try
		{
			if(conn!=null)
			{
				conn.close();}	
		}
		catch(SQLException e)
		{
			System.err.println("数据库连接关闭出错");
		}
	}
	
	
	public static void closeStatement(Statement smta)
	{
		try
		{
			if(smta!=null)
			{smta.close();}
		}
		catch(SQLException e)
		{
			System.err.println("数据库连接关闭出错");}
	}
	
	
	public static void closeResult(ResultSet rs)   //结果返回流
	{
		try
		{
			if(rs!=null)
			{	
				rs.close();}
		}
		catch(SQLException e)
		{
			System.err.println("数据库结果返回流关闭出错");}
	}
}
