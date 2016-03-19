package com.chekn.timer;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHtmlTest {
	
	public static void main(String[] args) throws IOException{
		String fp="C:\\Users\\CHEKN\\Desktop\\Ques.txt";
		Document doc= Jsoup.parse(new File(fp), "UTF-8");
		
		Element testExam=doc.getElementById("test_exam_4");
		
		Elements ques= testExam.select("#test_testTitle_4");
		System.out.println(ques.text());
		Elements options= testExam.select("#testItems_4 li");
		for(Element option:options){
			System.out.println(option.text());
		}
		
		
		Element testResult=doc.getElementById("testResult_4");
		Elements answer=testResult.select(".wrap-borders>div");
		System.out.println(answer.select(":lt(2)").text());
		System.out.println(answer.select(":eq(2)").text());
		System.out.println(answer.select(":gt(2)").text());
		
		
		Element noteWrapper=doc.getElementById("note-wrapper4");
		Elements note=noteWrapper.children();
		for(Element n:note){
			System.out.println(n.text());
		}
	}

}
