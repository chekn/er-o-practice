package com.ckn.practice;


import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpsUtil {

	/*
	 * //以下代码是定义HttpClient HttpClient client = new DefaultHttpClient();
	 * 
	 * //将client 转换
	 * 
	 * client = wrapClient(client);
	 */

	// 以下是wrapClient方法

	/**
	 * 获取可信任https链接，以避免不受信任证书出现peer not authenticated异常
	 * 
	 * @param base
	 * @return
	 */
	public static HttpClient wrapClient(HttpClient httpClient) {
		final java.security.cert.X509Certificate[] _AcceptedIssuers = new java.security.cert.X509Certificate[] {};
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return _AcceptedIssuers;
				}
				
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub
					
				}

				public void checkServerTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub
					
				}
			};
			ctx.init(null, new TrustManager[] { tm }, new SecureRandom());
			SSLSocketFactory ssf = new SSLSocketFactory(ctx,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));
			return new DefaultHttpClient(ccm, httpClient.getParams());
		} catch (Exception e) {
			System.out.println("=====:=====");
			e.printStackTrace();
		}
		return null;
	}

	public static String get(String url, String token) throws Exception {
		// 以下是client 的使用，可以和正常的client 使用一样了，，get方法使用

		// 定义HttpClient
		HttpClient client = new DefaultHttpClient();
		client = wrapClient(client);
		// 实例化HTTP方法
		HttpGet request = new HttpGet(url);
		request.setHeader("token",token);
		HttpResponse response = client.execute(request);
		
		return EntityUtils.toString(response.getEntity());

	}
	
	//异常 peer not authenticated  原因 证书验证不通过。就是说你所访问网站的证书不在你可以信任的证书列表中，下面是我的代码.
	public static void main(String[] args) {
		try {
			
			String txt = HttpsUtil.get("https://www.taobao.com/", "hw");
			System.out.println(txt);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}