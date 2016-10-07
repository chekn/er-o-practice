package com.test;
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
		new oracle.jdbc.driver.OracleDriver();       //��������
		String url="jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String name="chekn";
		String pwd="chekncom";

		//Class.forName("com.mysql.jdbc.Driver"); //����JDBC����
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();    //��Ҫ�����쳣
		}catch(Exception e)
		{
			System.err.println("JDBC�������س�������");
			e.printStackTrace();
		}
		
		try
		{
			conn=DriverManager.getConnection(url,name,pwd);}
		catch(SQLException e)
		{
			System.err.println("���Ӵ���");
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Connection getConnectionToServer()
	{
		new oracle.jdbc.driver.OracleDriver();       //��������
		String url="jdbc:oracle:thin:@192.168.1.200:1521:orcl";
		String name="scierp";
		String pwd="seechangecom";

		//Class.forName("com.mysql.jdbc.Driver"); //����JDBC����
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();    //��Ҫ�����쳣
		}catch(Exception e)
		{
			System.err.println("JDBC�������س�������");
			e.printStackTrace();
		}
		
		try
		{
			conn=DriverManager.getConnection(url,name,pwd);}
		catch(SQLException e)
		{
			System.err.println("���Ӵ���");
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection()    //�ر�����
	{
		try
		{
			if(conn!=null)
			{
				conn.close();}	
		}
		catch(SQLException e)
		{
			System.err.println("���ݿ����ӹرճ���");
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
			System.err.println("���ݿ����ӹرճ���");}
	}
	
	
	public static void closeResult(ResultSet rs)   //���������
	{
		try
		{
			if(rs!=null)
			{	
				rs.close();}
		}
		catch(SQLException e)
		{
			System.err.println("���ݿ����������رճ���");}
	}
}
