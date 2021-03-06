package com.ckn.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年6月28日-下午2:57:10
 */
public class NetResRead {
	
	/**
	 * Document doc = Jsoup.connect("http://example.com").userAgent("Mozilla").data("name", "jsoup").get(); 
	 *	Document doc = Jsoup.connect("http://example.com").cookie("auth", "token").post();
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		
		String path="http://www.xunread.com/article/b6f278f1-041b-4029-8fc7-fbb6d7c43fb8/";
		String indexPage="index.shtml";
		Document doc= Jsoup.connect(path+indexPage).get();
		Elements eles=doc.select("#content_1").get(0).children();
		StringBuilder bookStr=new StringBuilder();
		for(Element ele:eles){
			Element subA=ele.select("a").get(0);
			String chapterPage=subA.attr("href");
			String chapterName=subA.html();
			
			Document chapterDoc=Jsoup.connect(path+chapterPage).get();
			chapterDoc.select("#nav_1").remove();
			
			Element cele=chapterDoc.select("#content_1").get(0);
			String cnt=cele.html();
			String pretty=Jsoup.clean(cnt, "", Whitelist.none(), new OutputSettings().prettyPrint(false));
			StringBuilder strBu=new StringBuilder(chapterName).append("\n");
			String[] lines=pretty.split("\n");
			for(String line:lines) {
				strBu.append(line.replaceAll("^　*", ""));
				strBu.append("\n");
			}
			System.out.println("deal:"+chapterPage);
			bookStr.append(strBu);
			bookStr.append("\n\n\nchapter\n\n\n");
		}
		String classPath=NetResRead.class.getClassLoader().getResource("").getPath().toString().substring(1);
		String sourcePath=classPath+"../../src/main/java/";
		try {
			//FileUtils.write(new File(sourcePath+"ccc.pdf"), bookStr.toString());
			writeObject(sourcePath+"bookStr.tmp",bookStr);
			createPdf(sourcePath+"ccc.pdf", bookStr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * String html = <divhello <sworld</s</div<script type=\text/javascript\function hello(){alert(1);}</script<spanmy name is tony</span;
			Whitelist whitelist = Whitelist.relaxed();//创建relaxed的过滤器
			Document doc = Jsoup.parseBodyFragment(html);
			whitelist.addTags(span,s);//添加relaxed里面没有的标签
			Cleaner cleaner = new Cleaner(whitelist);
			doc = cleaner.clean(doc);//按照过滤器清除
			//doc.outputSettings().prettyPrint(false);//是否格式化
			Element body = doc.body();
			System.out.println(doc.html());//完整的html字符串
		 */
		
	}
	
	public static void createPdf(String fileName, String cnt) throws Exception {
		//step1
		com.itextpdf.text.Document doc=new com.itextpdf.text.Document(PageSize.A4);;
		
		//step2
		PdfWriter.getInstance(doc, new FileOutputStream(fileName));
		doc.addTitle("");
		doc.addAuthor("");
		doc.addSubject("");
		doc.addKeywords("");
		
		//step3
		doc.open();
		//step4
		System.out.println(cnt);
		BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,  "UniGB-UCS2-H" ,  false );
		//Font fontChinese =  new  Font(bfChinese,12,Font.NORMAL,Color.GREEN);  
		Font fontChinese =  new  Font(bfChinese, 12 , Font.NORMAL, BaseColor.BLACK);   
		doc.add(new Paragraph(cnt,fontChinese));
		
		//step5
		doc.close();
	}

	/**
	 * // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(HTML), Charset.forName("UTF-8"));
        // step 5
        document.close();
	 */
	
	/**
	 * 将对象序列化到磁盘文件中
	 * 序列化主要依赖java.io.ObjectOutputStream类,该类对java.io.FileOutputStream进一步做了封装,
	    这里主要使用ObjectOutputStream类的writeObject()方法实现序列化功能
	 * @param o
	 * @throws Exception
	 */
	public static void writeObject(String freezeObjFilePath,Object o) throws Exception{
		File f=new File(freezeObjFilePath);
		if(f.exists())
			f.delete();
		
		FileOutputStream os=new FileOutputStream(f);
		
		//ObjectOutputStream 核心类
		ObjectOutputStream oos =new ObjectOutputStream(os);
		oos.writeObject(o);
		
		oos.close();
		os.close();
	}
	
	/**
	 * 反序列化，将磁盘文件转化为对象
	 * 反序列化主要依赖java.io.ObjectInputStream类,该类对java.io.InputStream进一步做了封装,
	    这里主要使用ObjectInputStream类的readObject()方法实现反序列化功能。
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static Object readObject(File f) throws Exception{
		InputStream is=new FileInputStream(f);
		
		//ObjectInputStream 核心类
		ObjectInputStream ois=new ObjectInputStream(is);
		Object ot=ois.readObject();
		
		ois.close();
		is.close();
		
		return ot;
	}
	
	
}
