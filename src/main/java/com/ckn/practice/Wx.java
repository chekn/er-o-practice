package com.ckn.practice;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Wx {

	public static void main(String[] args) throws IOException {
		Document doc=Jsoup.connect("http://mp.weixin.qq.com/s?src=3&timestamp=1476322115&ver=1&signature=aG*sl7ozQvAxoPZJdFrdXUYsxyy04XZOWmwZP44MtH8ZV96rW1f6wCfx-KhZW4zFS719DzlM9Qe0EeFrF9i5ZvRSWyDHPFxBIXtO4T7Q3gDiUraTANwqslxxNWKVXokLksj4iXsLzbgX5dAcFMB3SzP75ShZX4oNn0Cu92Aczvg=").get();
		Element el= doc.select("#js_content").get(0);
		
		Elements sbs= el.select("img");
		for(Element sb:sbs) {
			String[] lns= sb.attr("data-s").split(",");
			sb.attr("src", sb.attr("data-src").replaceAll("\\d+(?=\\?)", lns[lns.length-1]));
			sb.removeAttr("data-src");
		}

		el.attr("style","margin:0 auto; width:670px;");
		System.out.println(el);
		
	}
	
}
