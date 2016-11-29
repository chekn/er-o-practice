package com.ckn.practice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.chekn.db.MySQLWorkDb;

/**
 * 
 * @author CodeFlagAI
 * @date 2016年11月26日-上午10:20:25
 */
public class RCKa {
	
	public static void main(String[] args) throws Exception {
		
		Connection conn =MySQLWorkDb.getConnection("ubaoguandb");
		String sql="select * from t_commodity_store";
		PreparedStatement ps=conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		 ResultSetMetaData rsmd = rs.getMetaData();
		 for(int i=1; i<=rsmd.getColumnCount();i++) {
			 String name = rsmd.getColumnName(i);
		System.out.println(name);
		 }
	}

}
