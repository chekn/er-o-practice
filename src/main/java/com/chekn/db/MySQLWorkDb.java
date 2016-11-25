package com.chekn.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
*/

public class MySQLWorkDb 
{
	private static Connection conn=null;
	
	public static Connection getConnection(){
		String defDb="weblog";	
		return MySQLWorkDb.getConnection("localhost", "root", "123456", defDb);
	}
	
	public static Connection getConnection(String db){
		String defDb=db;	
		return MySQLWorkDb.getConnection("localhost", "root", "123456", defDb);
	}
	
	public static Connection getConnection(String h, String u, String p, String db)
	{
		/*new oracle.jdbc.driver.OracleDriver();       //加载驱动
		String url="jdbc:oracle:thin:@F22011270:1521:oracle";
		String name="scott";
		String pwd="12345";*/						//首先着重修改一下数据库的默认字符集的编码
		String url="jdbc:mysql://"+ h +"/"+db+"?useUnicode=true&characterEncoding=utf8";   ///成功
		String name=u;
		String pwd=p;	

		/*Class.forName("com.mysql.jdbc.Driver"); //加载JDBC驱动*/
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();    //需要处理异常
		}catch(Exception e)
		{
			System.err.println("JDBC驱动加载出现问题");
		}
		
		/*catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    
		*/
		
		try
		{
			conn=DriverManager.getConnection(url,name,pwd);}
		catch(SQLException e)
		{
			System.err.println("连接错误");
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
