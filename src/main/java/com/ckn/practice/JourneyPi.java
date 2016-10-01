package com.ckn.practice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JourneyPi {

	public static void main(String[] args) throws Exception {
		String imu="http://you.autohome.com.cn/details/26759#205f2afd9bcf0fb62af2f93f1e46f24b";
		Document idoc=Jsoup.connect(imu).get();
		Elements ims=idoc.select(".article-img-box script");
		
		for (Element im : ims) {
			String sc=im.html();
			String scu=sc.replaceAll("^.*?(?=http)", "").replaceAll("(?<=\\.jpg).*$", "");
			dldImg(scu, "c:/carshot/"+UUID.randomUUID().toString()+".jpg");
			System.out.println(scu);
		}
	}
	
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
