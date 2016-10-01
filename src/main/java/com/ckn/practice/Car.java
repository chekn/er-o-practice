package com.ckn.practice;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Car {
	
	public static void main(String[] args) throws IOException {
		String b= FileUtils.readFileToString(new File("c:/car.txt"),"UTF-8");
		
		JSONObject jo= JSON.parseObject(b);
		JSONArray ji=jo.getJSONObject("result").getJSONArray("branditems");
		Object[] its= ji.toArray();
		
		for(Object it:its) {
			JSONObject i=((JSONObject)it);
			String id=i.getString("id");
			String na=(String) i.get("name");
			Document doc=Jsoup.connect("http://www.autohome.com.cn/ashx/AjaxIndexCarFind.ashx?type=3&value="+id).get();
			String sF=doc.body().text();
			FileUtils.writeStringToFile(new File("c:/car/"+String.format("%s_%s", id, na)+".txt"), sF, "UTF-8");

			System.out.println("x [read] get "+na);
		}
		
	}

}
