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
 
public class CopyOfHelloWorld extends AbstractHandler {
	
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		StringBuilder strBu= new StringBuilder();
    	try {
	    	String sql="select name, aid, price+0 as price, photos from ci_spec order by aid desc limit 0, 100";
			Connection conn=MySQLWorkDb.getConnection("ckn_ci");
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				String pco= rs.getString("photos");
				String[] pcs=pco.split(",");

				strBu.append("<script src='http://www.w3cways.com/demo/LazyLoad/js/jquery.js' ></script>");
				strBu.append("<script src='http://www.w3cways.com/demo/LazyLoad/js/jquery.lazyload.js' ></script>");
				strBu.append("<script  type='text/javascript'>$(function () { $('img.lazy').lazyload(); });</script>");
				
				strBu.append("<div>");
				strBu.append("<span> "+rs.getString("aid") +". "+ rs.getString("name")+ " ￥：" + rs.getString("price") +"</span><br>");
				
				if(pcs.length>1)
					for(String pc:pcs) 
						strBu.append("<img class='lazy' width='640' height='480' data-original='"+pc+"' /><br/>");
				
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
        server.setHandler(new CopyOfHelloWorld());
        server.start();
        server.join();
    }
}