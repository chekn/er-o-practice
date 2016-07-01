package com.ckn.practice.book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年6月30日-下午4:52:55
 */
public class MuBGet {
	private static Logger logger=Logger.getLogger(MuBGet.class);
	
	public static void main(String[] args) throws IOException {
		JsoupChapterGet chapterGetImpl=new JsoupChapterGet(){
			
			@Override
			public Chapter queryChapterInDoc(Document doc,String indexName) {
				logger.info("parse get cnt -> "+indexName);
				
				Element elem=doc.select("#contents").get(0);
				
				String cnt=elem.html();
				String pretty=Jsoup.clean(cnt, "", Whitelist.none(), new OutputSettings().prettyPrint(false));
				String[] lines=pretty.split("\n");
				
				List<String> cprls=new ArrayList<String>();
				for(String line:lines) {
					cprls.add(line.replaceAll("^　*", ""));
				}
				
				Chapter cpr=new Chapter();
				cpr.setChapterLines(cprls);
				
				return cpr;
			}
			
		};
		
		
		JsoupIndexsGet indexGetImpl=new JsoupIndexsGet(){
			private String bookHost="http://www.bookbao.net";
			
			@Override
			public String getIndexPageUrl() {
				return bookHost+"/book/201112/05/id_XMjIwNjkz.html";
			}
			
			@Override
			public List<Index> queryIndexsInDoc(Document doc) {
				List<Index> ixs=new ArrayList<Index>();
				
				Elements elems=doc.select(".info_chapterlist>ul li");
				for(Element elem:elems) {
					Element tagA=elem.select("a").get(0);
					Index ix=new Index();
					ix.setIndexUrl(bookHost+tagA.attr("href"));
					ix.setIndexName(tagA.html());
					ixs.add(ix);
				}
				
				return ixs;
			}
			
		};
		
		/**/
		List<String> cs=chapterGetImpl.queryChapter(indexGetImpl.queryBookIndexs().get(0)).getChapterLines();
		for(String c:cs)
			System.out.println(c);
		
		
		PdfOutput pop=new PdfOutput();
		MemOutput mop=new MemOutput();		
		
		BookGet bg=new BookGet(indexGetImpl, chapterGetImpl, Arrays.asList(pop,mop));
		try {
			//bg.go();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
