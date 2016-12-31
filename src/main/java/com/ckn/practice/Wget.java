package com.ckn.practice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;

import com.chekn.str.util.RegexUtils;

/**
 * 
 * @author CodeFlagAI
 * @date 2016年11月30日-下午2:49:26
 */
public class Wget {

	public static void main(String[] args) throws Exception {
		
		String str=FileUtils.readFileToString(new File("D:\\cc.txt"));
		
		List<String> urls=RegexUtils.extractCnt(str, "(?<=url\\().*?(?=\\))");
		
		HttpClient httpClient=getNewHttpClient();
		for(int i=0;i<urls.size();) {
			String url=urls.get(i);
			System.out.println(url);
			i++;
			/*try {
				System.out.println(FilenameUtils.getName("down "+FilenameUtils.getName(url) +"....."));
				HttpUtil.download(httpClient, url, null, "D:\\font\\"+FilenameUtils.getName(url));
				i++;
			} catch(Exception e) {
				e.printStackTrace();
				httpClient =getNewHttpClient();
			}*/
		}
		httpClient.getConnectionManager().shutdown();
	}
	
	public static HttpClient getNewHttpClient() {
		HttpClient httpClient=new DefaultHttpClient();
		 String proxyHost = "127.0.0.1";
		 int proxyPort = 1080;
		 HttpHost proxy = new HttpHost(proxyHost,proxyPort);
		 httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
		 return httpClient;
	}
}
