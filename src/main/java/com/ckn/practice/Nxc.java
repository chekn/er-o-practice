package com.ckn.practice;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Nxc {

	public static void main(String[] args) throws Exception {
		Document doc=Jsoup.connect("http://sh.122.gov.cn/cmspage/jgzx/index.html").get();
		Elements els= doc.select(".news-list >li");
		for (Element el:els) {
			System.out.println(el.text());
			Element a =el.select("a").get(0);
			String link=a.absUrl("href");
			String title=a.text();
			Element p=el.select("p").get(0);
			Date date=new SimpleDateFormat("yyyy-MM-dd" ).parse(p.text().replace("发布时间:", ""));
			if(DateUtils.isSameDay(date, new Date()) || true) {
				System.out.println(link);
				Document cnt= Jsoup.connect(link).get();
				Element cnte=cnt.getElementsByTag("article").get(0);
				
				System.out.println(cnte.toString());
			}
			
			//System.out.println(RandomStringUtils.randomAlphanumeric(8));;
		}
		
	}
	
}
