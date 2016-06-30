package com.ckn.practice.book;

import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年6月30日-下午4:52:55
 */
public class MuBGet {
	
	public static void main(String[] args) {
		JsoupChapterGet chapterGetImpl=new JsoupChapterGet(){
			
			@Override
			public Chapter queryChapterInDoc(Document doc) {
				return null;
			}
			
		};
		
		JsoupIndexsGet indeGetImpl=new JsoupIndexsGet(){
			
			@Override
			public String getIndexPageUrl() {
				return null;
			}
			
			@Override
			public List<Index> queryIndexsInDoc(Document doc) {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
		
		PdfOutput pop=new PdfOutput();
		MemOutput mop=new MemOutput();		
		
		BookGet bg=new BookGet(indeGetImpl, chapterGetImpl, Arrays.asList(pop,mop));
		try {
			bg.go();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
