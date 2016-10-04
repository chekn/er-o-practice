package com.ckn.practice;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chekn.db.MySQLWorkDb;


public class SubCar extends TestCase {
	

	public static void main(String[] args) throws Exception {		
		/*File dir=new File("c:/car");
		for(File file: dir.listFiles()) {
			String fn=file.getName();
			if("txt".equals( FilenameUtils.getExtension(fn)) ) {
				String bd=FilenameUtils.getBaseName(fn);
				sibd(bd.replaceAll("^\\d*_", ""));
			}
		}*/
		
		fcd();
	}
	
	public static void g2f() throws IOException {
		File dir=new File("c:/car");
		for(File file: dir.listFiles()) {
			String fn=file.getName();
			if("txt".equals( FilenameUtils.getExtension(fn)) ) {
				String sfc=FileUtils.readFileToString(file);
				
				String bd=FilenameUtils.getBaseName(fn);
				String p1="c:/car/"+bd;
				FileUtils.forceMkdir(new File(p1));
				
				JSONObject jo= JSON.parseObject(sfc);
				JSONArray ji=jo.getJSONObject("result").getJSONArray("factoryitems");
				Object[] its= ji.toArray();
				
				for(Object it:its) {
					JSONObject i=((JSONObject)it);
					String id=i.getString("id");
					String fa=(String) i.get("name");
					
					String fcd=p1+"/"+fa;
					FileUtils.forceMkdir(new File(fcd));
					
					JSONArray sis=i.getJSONArray("seriesitems");
					for(Object si:sis.toArray()) {
						JSONObject sii=((JSONObject)si);
						String sid=sii.getString("id");
						String sina=sii.getString("name");
						
						Document doc=Jsoup.connect("http://www.autohome.com.cn/ashx/AjaxIndexCarFind.ashx?type=5&value="+sid).get();
						String sF=doc.body().text();
						
						FileUtils.writeStringToFile(new File(fcd+"/"+String.format("%s_%s", sid, sina)+".txt"), sF, "UTF-8");
						println("\t\t x [get] "+ sina);
					}
					
					println("\t x [seek] end "+ fa);
				}
				println("x [bd] end "+ bd);
			}
		}
		
		FileUtils.writeStringToFile(new File("c:/c.txt"), strBu.toString(), "UTF-8");
	}

	public static StringBuilder strBu=new StringBuilder();
	
	public static void println(String text) {
		System.out.println(text);
		
		strBu.append(text);
		strBu.append("\r\n");
	}
	
	
	public static void sibd(String bn) throws Exception {
		String url="http://baike.baidu.com/search/word?word="+ URLEncoder.encode(bn, "UTF-8");
		Document doc=Jsoup.connect(url).get();
		System.out.println("x Bak: "+bn + " > " + url);
		//System.out.println(doc.toString());
		
		Elements tms=doc.select(".polysemantList-wrapper");
		if(tms.size()>=1) {
			Elements ims= tms.get(0).select(".item a");
			for(Element im:ims) {
				if(im.text().contains("汽车")) {
					System.out.println("  multi-jmp: " + im.text() + " > " + im.absUrl("href"));
					doc=Jsoup.connect(im.absUrl("href")).get();
					break;
				}
			}
			
		}
		
		/*if( 1==1 )
			return;*/
		
		Elements els= doc.select(".basic-info .basicInfo-item");
		int i=0;
		StringBuilder fmt=new StringBuilder();
		for(Element el:els) {
			fmt.append(el.text()+ ( i%2==1 ? "\n" : " - " ));
			i++;
		}
		Elements pics= doc.select(".summary-pic > a > img");
		String pul=pics.size()>0 ? pics.get(0).absUrl("src") : null;
		
		String fmts=fmt.toString();
		String en_name=null;
		String cty=null;
		String btc=null;
		String ct=null;
		for(String s: fmts.split("\n")) {
			//System.out.println(ArrayUtils.toString(s.split(" - ")));
			String[] si= s.split(" - ");
			if( si[0].contains("外文") ) {
				en_name=si[1];
			}
			if( si[0].contains("总部") ) {
				cty=si[1];
			}
			if( si[0].contains("所属") ) {
				btc= si[1];
			}
			if( si[0].contains("成立") ) {
				ct = si[1];
			}
		}
		System.out.println("\tx if: "+ en_name + " - "+cty + " - " + btc + " - "+ct);
		String sql="insert into ci_brand (id, name, en_name, btc, create_time, pul, info) values('"+
				UUID.randomUUID().toString().replaceAll("-", "")+"','"+
				bn+"','"+
				en_name+"','"+
				btc+"','"+
				ct+"','"+
				pul+"','"+
				fmts+"')";
		
		System.out.println("\t os:"+sql.replace("\n", ""));
		Connection conn=MySQLWorkDb.getConnection("ckn_ci");
		PreparedStatement ps= conn.prepareStatement(sql);
		ps.executeUpdate();
		MySQLWorkDb.closeStatement(ps);
		MySQLWorkDb.closeConnection();
	}
	
	public static void fcd() throws Exception {
		File dir=new File("c:/car");
		for(File file: dir.listFiles()) {
			String fn=file.getName();
			if("txt".equals( FilenameUtils.getExtension(fn)) ) {
				String sfc=FileUtils.readFileToString(file);
				
				String bd=FilenameUtils.getBaseName(fn);
				System.out.println("\tx re :" + bd );
				String p1="c:/car/"+bd;
				FileUtils.forceMkdir(new File(p1));
				
				JSONObject jo= JSON.parseObject(sfc);
				JSONArray ji=jo.getJSONObject("result").getJSONArray("factoryitems");
				Object[] its= ji.toArray();
				
				for(Object it:its) {
					JSONObject i=((JSONObject)it);
					String id=i.getString("id");
					String fa=(String) i.get("name");
					
					Connection conn=MySQLWorkDb.getConnection("ckn_ci");
					PreparedStatement ps= conn.prepareStatement("select id from ci_brand where name='"+bd.replaceAll("^\\d*_", "")+"'");
					ResultSet rs= ps.executeQuery();
					if(!rs.next())
						throw new RuntimeException("!!!! s v b:"+bd.replaceAll("^\\d*_", ""));
					String bid= rs.getString(1);
					PreparedStatement ps2= conn.prepareStatement("insert into ci_fac values(REPLACE(UUID(),'-',''),'"+fa+"','"+bid+"')");
					ps2.executeUpdate();
					MySQLWorkDb.closeStatement(ps);
					MySQLWorkDb.closeConnection();
					System.out.println("\tx ins :" + fa );
				}
			}
		}
	}
	
	
	public void indb() throws Exception {
		File dir=new File("c:/car");
		for(File file: dir.listFiles()) {
			String fn=file.getName();
			if("txt".equals( FilenameUtils.getExtension(fn)) ) {
				String sfc=FileUtils.readFileToString(file);
				
				String bd=FilenameUtils.getBaseName(fn);
				String p1="c:/car/"+bd;
				
				JSONObject jo= JSON.parseObject(sfc);
				JSONArray ji=jo.getJSONObject("result").getJSONArray("factoryitems");
				Object[] its= ji.toArray();
				
				for(Object it:its) {
					JSONObject i=((JSONObject)it);
					String id=i.getString("id");
					String fa=(String) i.get("name");
					
					JSONArray sis=i.getJSONArray("seriesitems");
					for(Object si:sis.toArray()) {
						JSONObject sii=((JSONObject)si);
						String sid=sii.getString("id");
						String sina=sii.getString("name");
						
						Connection conn=MySQLWorkDb.getConnection("ckn_ci");
						PreparedStatement ps= conn.prepareStatement("select id from ci_fac where name='"+fa+"'");
						ResultSet rs= ps.executeQuery();
						if(!rs.next())
							throw new RuntimeException("!!!! s v b:"+fa);
						String fid= rs.getString(1);
						PreparedStatement ps2= conn.prepareStatement("insert into ci_series values(REPLACE(UUID(),'-',''),'"+sina+"','"+fid+"')");
						ps2.executeUpdate();
						MySQLWorkDb.closeStatement(ps);
						MySQLWorkDb.closeConnection();
						println("\t\t x [ins] "+ sina);
					}
					
					println("\t x [seek] end "+ fa);
				}
				println("x [bd] end "+ bd);
			}
		}
		
		//FileUtils.writeStringToFile(new File("c:/c.txt"), strBu.toString(), "UTF-8");
	}
	

}
