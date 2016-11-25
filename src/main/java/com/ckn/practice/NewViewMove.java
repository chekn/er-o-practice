package com.ckn.practice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chekn.db.MySQLWorkDb;

/**
 * 
 * @author CodeFlagAI
 * @date 2016年11月25日-下午9:12:09
 */
public class NewViewMove {
	
	public static void main(String[] args) throws SQLException{
		Connection conn= MySQLWorkDb.getConnection("ubaoguandb");
		
		String sql="SELECT table_schema, table_name, view_definition from information_schema.VIEWS;";
		PreparedStatement ps= conn.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		
		List<String> sqlTxts= new ArrayList<String>();
		while(rs.next()) {
			sqlTxts.add("create view "+ rs.getString("table_schema") + "." + rs.getString("table_name")+ " as "+ rs.getString("view_definition"));
		}
		
		Connection connServer= MySQLWorkDb.getConnection("rm-uf6s510w41cc14r5io.mysql.rds.aliyuncs.com", "julianfish", "xinhaoqi-1024", "ubaoguandb");
		for(String sqlTxt:sqlTxts) {
			System.out.println(sqlTxt);
			try {
				createView(connServer, sqlTxt);
			} catch(Exception e) {
				System.err.println(e.getMessage());
				//String table =e.getMessage().replaceAll(ta"", replacement);
			}
		}
		
		
	}
	
	public static void createView(Connection conn, String sql) throws SQLException {
		PreparedStatement ps= conn.prepareStatement(sql);
		ps.execute();
	}

}
