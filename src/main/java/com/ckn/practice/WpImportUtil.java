package com.ckn.practice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class WpImportUtil {
	
	public static void addRequestHeader(HttpRequest request){

		request.setHeader("Host","127.0.0.1");
		request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
		request.setHeader("Accept","*/*");
		request.setHeader("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		request.setHeader("Accept-Encoding","gzip, deflate");
		request.setHeader("DNT","1");
		request.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		request.setHeader("X-Requested-With","XMLHttpRequest");
		request.setHeader("Referer","http://127.0.0.1/wordpress/wp-admin/edit-tags.php?taxonomy=post_tag");
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
	
	/**
	 * 添加标签
	 * @param httpClient
	 * @param cookie
	 * @param tagName
	 * @param slug
	 * @return
	 * @throws Exception
	 */
	public static String addTag(HttpClient httpClient, String cookie, String name, String slug) throws Exception{
		List<NameValuePair> nvps=new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("_wp_http_referer", "/wordpress/wp-admin/edit-tags.php?taxonomy=post_tag"));

		nvps.add(new BasicNameValuePair("post_type", "post"));
		nvps.add(new BasicNameValuePair("_wpnonce_add-tag", "6f4e67f165"));
		
		nvps.add(new BasicNameValuePair("action", "add-tag"));
		nvps.add(new BasicNameValuePair("screen", "edit-post_tag"));
		nvps.add(new BasicNameValuePair("taxonomy", "post_tag"));
		
		nvps.add(new BasicNameValuePair("tag-name", name));
		nvps.add(new BasicNameValuePair("slug", slug));
		nvps.add(new BasicNameValuePair("description", ""));
		
		String pts= WpImportUtil.post(httpClient, 
						"http://127.0.0.1/wordpress/wp-admin/admin-ajax.php", 
						cookie,
						nvps);
		
		return pts;
	}
	
	/**
	 * 添加分类
	 * @param httpClient
	 * @param cookie
	 * @param name
	 * @param slug
	 * @return
	 * @throws Exception
	 */
	public static String addCategory(HttpClient httpClient, String cookie, String name,String slug) throws Exception{
		List<NameValuePair> nvps=new ArrayList<NameValuePair>();
		
		nvps.add(new BasicNameValuePair("action","add-tag"));
		nvps.add(new BasicNameValuePair("screen","edit-category"));
		nvps.add(new BasicNameValuePair("taxonomy","category"));
		nvps.add(new BasicNameValuePair("post_type","post"));
		nvps.add(new BasicNameValuePair("_wpnonce_add-tag","5e37ea3ed6"));
		nvps.add(new BasicNameValuePair("_wp_http_referer","/wordpress/wp-admin/edit-tags.php?taxonomy=category"));
		
		nvps.add(new BasicNameValuePair("parent","-1"));
		
		nvps.add(new BasicNameValuePair("tag-name",name));
		nvps.add(new BasicNameValuePair("slug",slug));
		nvps.add(new BasicNameValuePair("description",""));

		String pts=WpImportUtil.post(httpClient, "http://127.0.0.1/wordpress/wp-admin/admin-ajax.php", cookie, nvps);
		
		return pts;
	}
	
	public static String toNewArticle(HttpClient httpClient, String cookie) throws Exception{
		String pts=WpImportUtil.post(httpClient, "http://127.0.0.1/wordpress/wp-admin/post-new.php", cookie, null);
		return pts;
	}
	
	public static String addArticle(HttpClient httpClient, String cookie,String aid, String atit, String acon) throws Exception{
		List<NameValuePair> nvps=new ArrayList<NameValuePair>();
		Date now =new Date();
		
		String yy=(now.getYear()+1900)+""; String mm=(now.getMonth()+1)+""; String dd=(now.getDay()+1)+""; 
		String hh=(now.getHours())+""; String mi=(now.getMinutes())+""; String ss=(now.getSeconds())+"";
		
		nvps.add(new BasicNameValuePair("_wp_http_referer","/wordpress/wp-admin/post-new.php"));
		nvps.add(new BasicNameValuePair("user_ID","1"));
		nvps.add(new BasicNameValuePair("post_author","1"));
		nvps.add(new BasicNameValuePair("action","editpost"));
		nvps.add(new BasicNameValuePair("originalaction","editpost"));
		nvps.add(new BasicNameValuePair("post_type","post"));
		nvps.add(new BasicNameValuePair("original_post_status","auto-draft"));
		nvps.add(new BasicNameValuePair("referredby","http://127.0.0.1/wordpress/wp-admin/edit-tags.php?taxonomy=category"));
		nvps.add(new BasicNameValuePair("_wp_original_http_referer","http://127.0.0.1/wordpress/wp-admin/edit-tags.php?taxonomy=category"));
		nvps.add(new BasicNameValuePair("auto_draft","1"));

		nvps.add(new BasicNameValuePair("_wpnonce","ff26a9ec80"));
		nvps.add(new BasicNameValuePair("meta-box-order-nonce","6458842e4c"));					//固定值
		nvps.add(new BasicNameValuePair("closedpostboxesnonce","ae80981af3"));
		nvps.add(new BasicNameValuePair("samplepermalinknonce","575589db52"));
		
		nvps.add(new BasicNameValuePair("wp-preview",""));
		
		nvps.add(new BasicNameValuePair("hidden_post_password",""));
		nvps.add(new BasicNameValuePair("post_password",""));
		
		nvps.add(new BasicNameValuePair("aa",yy));
		nvps.add(new BasicNameValuePair("mm",mm));
		nvps.add(new BasicNameValuePair("jj",dd));
		nvps.add(new BasicNameValuePair("hh",hh));
		nvps.add(new BasicNameValuePair("mn",mi));
		nvps.add(new BasicNameValuePair("ss",ss));
		
		nvps.add(new BasicNameValuePair("hidden_aa", yy));
		nvps.add(new BasicNameValuePair("cur_aa", yy));
		nvps.add(new BasicNameValuePair("hidden_mm",mm));
		nvps.add(new BasicNameValuePair("cur_mm",mm));
		nvps.add(new BasicNameValuePair("hidden_jj",dd));
		nvps.add(new BasicNameValuePair("cur_jj",dd));
		nvps.add(new BasicNameValuePair("hidden_hh",hh));
		nvps.add(new BasicNameValuePair("cur_hh",hh));
		nvps.add(new BasicNameValuePair("hidden_mn",mi));
		nvps.add(new BasicNameValuePair("cur_mn",mi));
		
		nvps.add(new BasicNameValuePair("original_publish","发布"));
		nvps.add(new BasicNameValuePair("publish","发布"));
		nvps.add(new BasicNameValuePair("newcategory","新分类目录名"));
		nvps.add(new BasicNameValuePair("newcategory_parent","-1"));
		nvps.add(new BasicNameValuePair("_ajax_nonce-add-category","2ca293e44e"));
		
		nvps.add(new BasicNameValuePair("excerpt",""));
		nvps.add(new BasicNameValuePair("trackback_url",""));
		nvps.add(new BasicNameValuePair("metakeyselect","#NONE#"));
		nvps.add(new BasicNameValuePair("metakeyinput",""));
		nvps.add(new BasicNameValuePair("metavalue",""));
		nvps.add(new BasicNameValuePair("_ajax_nonce-add-meta","be518c0e18"));
		nvps.add(new BasicNameValuePair("advanced_view","1"));
		
		nvps.add(new BasicNameValuePair("comment_status","open"));
		nvps.add(new BasicNameValuePair("ping_status","open"));
		nvps.add(new BasicNameValuePair("post_author_override","1"));
		nvps.add(new BasicNameValuePair("post_name",""));
		nvps.add(new BasicNameValuePair("newtag[post_tag]",""));
			
		nvps.add(new BasicNameValuePair("hidden_post_visibility","public"));		//文章公开度
		nvps.add(new BasicNameValuePair("visibility","public"));
		
		nvps.add(new BasicNameValuePair("post_status","draft"));					//文章状态, 已发布
		nvps.add(new BasicNameValuePair("hidden_post_status","draft"));

		
		nvps.add(new BasicNameValuePair("post_ID",aid));							//文章ID

		nvps.add(new BasicNameValuePair("post_title",atit));
		nvps.add(new BasicNameValuePair("content",acon));
		
		nvps.add(new BasicNameValuePair("post_category[]","0"));					//分类信息
		nvps.add(new BasicNameValuePair("post_category[]","26"));					
		
		nvps.add(new BasicNameValuePair("tax_input[post_tag]","LAF1、LAF2"));		//标签

		String pts=WpImportUtil.post(httpClient, "http://127.0.0.1/wordpress/wp-admin/post.php", cookie, nvps);
		
		return pts;
	}
	
	public static String loadBdArticle(HttpClient httpClient, String cookie) throws Exception{
		
		String pts=WpImportUtil.post(httpClient, "http://wenzhang.baidu.com/", cookie, null);
		return pts;
		
	}
	
	public static void main(String[] args) throws Exception{
		/*String cookie="wordpress_5bd7a9c61cda6e66fc921a05bc80ee93=root%7C1446600347%7CsGkDb56LZzq1tjKJV0KTPYTOv5rE8IegcIOhXaz1aJr%7C62886bc1bb536799abcb307241e168dbc7f2c570839e6eae3dcf22a1241cdd81"
				+"wordpress_test_cookie=WP+Cookie+check"
				+"wordpress_logged_in_5bd7a9c61cda6e66fc921a05bc80ee93=root%7C1446600347%7CsGkDb56LZzq1tjKJV0KTPYTOv5rE8IegcIOhXaz1aJr%7C552fa48415846de6f91f97e7706ad6057f54e6cd9244e43210a88f7717e86484"
				+"wp-settings-1=editor%3Dtinymce%26libraryContent%3Dupload%26hidetb%3D1"
				+"wp-settings-time-1=1446447390"
				+"Hm_lvt_2d83c9aaf78b60ce1746ef946ffd5e2b=1441692705,1441693385"
				+"_ga=GA1.1.685618315.1441533027"
				+"Hm_lvt_bb00cafe3a44c859da007790300138bf=1445915787"
				+"Hm_lvt_b593fe6dbbc3a165ec20a9a9512890f5=1445915787";*/
		String cookie="wordpress_5bd7a9c61cda6e66fc921a05bc80ee93=root%7C1446600347%7CsGkDb56LZzq1tjKJV0KTPYTOv5rE8IegcIOhXaz1aJr%7C62886bc1bb536799abcb307241e168dbc7f2c570839e6eae3dcf22a1241cdd81; wordpress_test_cookie=WP+Cookie+check; wordpress_logged_in_5bd7a9c61cda6e66fc921a05bc80ee93=root%7C1446600347%7CsGkDb56LZzq1tjKJV0KTPYTOv5rE8IegcIOhXaz1aJr%7C552fa48415846de6f91f97e7706ad6057f54e6cd9244e43210a88f7717e86484; wp-settings-1=editor%3Dtinymce%26libraryContent%3Dupload%26hidetb%3D1; wp-settings-time-1=1446447390; Hm_lvt_2d83c9aaf78b60ce1746ef946ffd5e2b=1441692705,1441693385; _ga=GA1.1.685618315.1441533027; Hm_lvt_bb00cafe3a44c859da007790300138bf=1445915787; Hm_lvt_b593fe6dbbc3a165ec20a9a9512890f5=1445915787";
		
		HttpClient httpClient=new DefaultHttpClient();
		
		/*for(int i=80;i<88;i++){
			String pts2=WpImportUtil.toNewArticle(httpClient, cookie);
			String pts= WpImportUtil.addArticle(httpClient, cookie, Integer.toString(i), "title"+i, "content"+i);
			System.out.println(pts2);
			System.out.println(pts);
		}*/
		String pts=WpImportUtil.loadBdArticle(httpClient, "BDUSS=hhbS1qMHlEWDhUN0pJem1zSGdOMVNJMzZEdENjZklFc0dWQkdZUGw3VGRCajFWQVFBQUFBJCQAAAAAAAAAAAEAAABsnzoNteHO9-bkAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAN15FVXdeRVVVF; BAIDUID=6D2E4554A8AC84E50746EE8EA37C2834:FG=1; SCPA_IMAGEPLACE=ok2668176101IMAGEPLACE; Hm_lvt_19f7e3b89626f41825e5c15696da95c5=1432316054,1432316620,1432317864,1432317898; BIDUPSID=49C49C01848C276F9491B65C9628D7E4; PSTM=1438702314; H_PS_PSSID=11194_1423_17743_17620_17813_12825_17782_17501_17000_17073_15650_11465_14553_17157; SCMOBLE=00-00-00d4GjyUjqOy");
		System.out.println(pts);
		
		httpClient.getConnectionManager().shutdown();
		
		/*
		Date now=new Date();
		String yy=(now.getYear()+1900)+""; String mm=(now.getMonth()+1)+""; String dd=(now.getDay()+1)+""; 
		String hh=(now.getHours())+""; String mi=(now.getMinutes())+""; String ss=(now.getSeconds())+"";
		System.out.println("年_"+yy+"; 月_"+mm+"; 日_"+dd+"; 时_"+hh+"; 分_"+mi+"; 秒_"+ss);
		*/
	}
	
}

class RevData{
	
}
