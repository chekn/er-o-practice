package com.ckn.practice;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	
	public static void addRequestHeader(HttpRequest request){

		//request.setHeader("Host","127.0.0.1");
		request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
		request.setHeader("Accept","*/*");
		request.setHeader("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		request.setHeader("Accept-Encoding","gzip, deflate");
		request.setHeader("DNT","1");
		request.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		request.setHeader("X-Requested-With","XMLHttpRequest");
		request.setHeader("Referer","");
		//request.setHeader("Content-Length","212");
		//request.setHeader("Cookie","wordpress_5bd7a9c61cda6e66fc921a05bc80ee93=root%7C1446358170%7CzGernrdzX5rYP9LJa85fFZpmFgnTBNXaDYmswB3Obxf%7Ceac4be129a42b120c756828de5f1bed0aae6bb69574e2d79743d282567614170; wp-settings-time-1=1446185370; wordpress_test_cookie=WP+Cookie+check; wordpress_logged_in_5bd7a9c61cda6e66fc921a05bc80ee93=root%7C1446358170%7CzGernrdzX5rYP9LJa85fFZpmFgnTBNXaDYmswB3Obxf%7C479b0037ffb5d22b67b2730fc39a9a19bb7e1fa58fd11b8e66fc2862dd746c05; wp-settings-1=editor%3Dtinymce%26libraryContent%3Dupload%26hidetb%3D0; _ga=GA1.1.694463914.1445909372; Hm_lvt_b593fe6dbbc3a165ec20a9a9512890f5=1445909375; Hm_lvt_bb00cafe3a44c859da007790300138bf=1445909375");
		request.setHeader("Connection","keep-alive");
		request.setHeader("Pragma","no-cache");
		request.setHeader("Cache-Control","no-cache");
		
	}
	
	public static String post(HttpClient activeClient, String url, String cookie, List<NameValuePair> kvs) throws Exception{
		HttpPost hp=new HttpPost(url);
		//addRequestHeader(hp);
		hp.setHeader("cookie", cookie);
		hp.setEntity(new UrlEncodedFormEntity(kvs==null?(new ArrayList<NameValuePair>()):kvs,"UTF-8"));
		HttpResponse hr=activeClient.execute(hp);
		String rt=EntityUtils.toString(hr.getEntity(),"UTF-8");
		
		return rt;
	}
	

	public static void download(HttpClient activeClient, String url, String cookie, String filePath) throws Exception{
		HttpGet hg=new HttpGet(url);
		//addRequestHeader(hp);
		hg.setHeader("cookie", cookie);
		HttpResponse hr=activeClient.execute(hg);
		InputStream ris= hr.getEntity().getContent();
		
		byte [] bytes = IOUtils.toByteArray(ris);
		FileUtils.writeByteArrayToFile(new File(filePath),bytes);
		
		IOUtils.closeQuietly(ris);
	}
	
	
	public static void main(String[] args) throws Exception{
		
		HttpClient httpClient=new DefaultHttpClient();
		
		String url="http://720kan5.345lu.cc/d800b8ba865bba5c3d346ecdc2c4c413/d800b8ba865bba5c3d346ecdc2c4c413.mp4";
		String filePath="c:\\cc\\cc.mp4";
		HttpUtil.download(httpClient, url, null, filePath);
		
		httpClient.getConnectionManager().shutdown();
		
	}
	
}

