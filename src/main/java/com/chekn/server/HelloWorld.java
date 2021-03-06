package com.chekn.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.chekn.db.MySQLWorkDb;
 
public class HelloWorld extends AbstractHandler {
	
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		StringBuilder strBu= new StringBuilder();
    	try {
	    	String sql="select name, en_name, pul, info from ci_brand order by length(pul) desc";
			Connection conn=MySQLWorkDb.getConnection("ckn_ci");
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				strBu.append("<div>");
				strBu.append("<span>"+rs.getString("name") +" : "+ rs.getString("en_name")+"</span><br>");
				strBu.append("<img src='"+rs.getString("pul")+"' /><br/>");
				strBu.append("<pre>"+rs.getString("info")+"</pre>");
				strBu.append("</div>");
				strBu.append("<br/><hr/><br/>");
			}
			
			MySQLWorkDb.closeStatement(ps);
			MySQLWorkDb.closeConnection();
		} catch (SQLException e) {
			strBu.append("出错了\n" + e.getMessage());
		}
    	
    	response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(strBu.toString());
        baseRequest.setHandled(true);
    }
 
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new HelloWorld());
        server.start();
        server.join();
    }
}