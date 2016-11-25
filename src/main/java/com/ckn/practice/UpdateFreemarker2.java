package com.ckn.practice;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年3月28日-下午7:10:40
 */
public class UpdateFreemarker2 {
	
	private static String test(Map<String, String> data) throws Exception{
		Configuration cfg=new Configuration(Configuration.VERSION_2_3_23);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setClassForTemplateLoading(UpdateFreemarker2.class, "/"+UpdateFreemarker2.class.getPackage().getName().replace(".", "/"));
		
		Template tpt=cfg.getTemplate("test2.ftl");
		
		StringWriter sw=new StringWriter();
		tpt.process(Collections.singletonMap("td", data), sw);
		
		return sw.toString() ;
	}
	
	public static void main(String[] args) throws Exception {
		File file= new File("D:\\file.html");
		Document doc= Jsoup.parse(file, "UTF-8");
		
		StringBuilder strBu= new StringBuilder();
		Elements trs= doc.select("table tr");
		for(Element tr:trs) {
			Elements tds= tr.select("td");
			if(tds.size()==6) {
				String t1 = tds.get(0).text();
				String i1 = tds.get(1).select("input").last().attr("name");
				String t2 = tds.get(2).text();
				String i2 = tds.get(3).select("input").last().attr("name");
				String t3 = tds.get(4).text();
				String i3 = tds.get(5).select("input").last().attr("name");
				
				Map<String,String> map = new HashMap<String,String>();
				map.put("t", t1);
				map.put("i",i1);
				strBu.append(test(map));
				map.put("t", t2);
				map.put("i",i2);
				strBu.append(test(map));
				map.put("t", t3);
				map.put("i",i3);
				strBu.append(test(map));
			} else if(tds.size() == 4) {
				String t1 = tds.get(0).text();
				String i1 = tds.get(1).select("input").toString();
				String t2 = tds.get(2).text();
				String i2 = tds.get(3).select("input").toString();
				Map<String,String> map = new HashMap<String,String>();
				map.put("t", t1);
				map.put("i",i1);
				strBu.append(test(map));
				map.put("t", t2);
				map.put("i",i2);
				strBu.append(test(map));
			} else if(tds.size() ==2) {
				String t1 = tds.get(0).text();
				String i1 = tds.get(1).select("input").toString();
				Map<String,String> map = new HashMap<String,String>();
				map.put("t", t1);
				map.put("i",i1);
				strBu.append(test(map));
			} else {
				System.err.println(" err in tr" + tr.toString());
			}
		}
		System.out.print(strBu.toString());
	}
	
	
}
