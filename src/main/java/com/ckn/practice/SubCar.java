package com.ckn.practice;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class SubCar {
	

	public static void main(String[] args) throws IOException {
		
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

}
