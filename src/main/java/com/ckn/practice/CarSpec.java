package com.ckn.practice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CarSpec {
	
	public static void main(String[] args) throws Exception {
		String fx=FileUtils.readFileToString(new File("C:\\car\\36_奔驰\\北京奔驰\\2562_奔驰GLK级.txt"));
		JSONArray yis=((JSONObject)JSON.parse(fx)).getJSONObject("result").getJSONArray("yearitems");
		
		for(Object yi:yis.toArray()) {
			JSONObject oyi =(JSONObject) yi;
			JSONArray sis= oyi.getJSONArray("specitems");
			
			for(Object si:sis.toArray()) {
				JSONObject osi =(JSONObject) si;
				gIP(osi.getInteger("id").toString());
			}
		}
	}
	
	public static void gIP(String csi) throws Exception {
		String cu="http://www.autohome.com.cn/spec/"+csi+"/#pvareaid=10006";
		Document doc=Jsoup.connect(cu).get();
		Element e=doc.select(".cardetail-infor-car").get(0);
		
		Elements is= e.select("ul > li");
		for(Element i:is) {
			String tx=i.text();
			String[] nv=tx.split("：");
			if( !ncare.contains(nv[0]) ) {
				System.out.println(nv[0] +"-"+ nv[1]);
			}
		}
		
		String imu="http://car.autohome.com.cn/photolist/spec/"+csi+"/1/p1/?pvareaid=101474";
		Document idoc=Jsoup.connect(imu).get();
		Elements ims=idoc.select("#pa1 > ul >li");
		
		for(Element im:ims) {
			Element ia= im.child(0);
			String iam=ia.attr("href").replaceAll("^/", "http://car.autohome.com.cn/").replace("photo", "bigphoto");
			Document iadoc= Jsoup.connect(iam).get();
			Element ia1= iadoc.select("#img").get(0);
			
			dldImg(ia1.absUrl("src"), "c:/carshot/"+UUID.randomUUID().toString()+".jpg");
			System.out.println("read "+iam + " out down");
		}
	}
	
	public static List<String> ncare =Arrays.asList("查看详细参数配置>>");
	
	public static void dldImg(String ul, String path) throws Exception {
		URL   url   =   new   URL( ul); 
		URLConnection   uc   =   url.openConnection(); 
		InputStream   is   =   uc.getInputStream(); 
		File   file   =   new   File( path); 
		FileOutputStream   out   =   new   FileOutputStream(file); 
		
		IOUtils.copy(is, out);
		IOUtils.closeQuietly(is);
		IOUtils.closeQuietly(out);
	}
}
