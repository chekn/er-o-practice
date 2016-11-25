package com.chekn.str.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	
	public static List<String> extractTagHTML(String cnt, String tagName, boolean isContainTag){
		List<String> rev=new ArrayList<String>();
		
		Pattern pattern=Pattern.compile("<"+tagName+">([\\s\\S]*?)</"+tagName+">");
		Matcher matcher=pattern.matcher(cnt);
		while(matcher.find()){
			String inCnt=matcher.group(isContainTag?0:1);
			rev.add(inCnt);
		}
		
		return rev;
	}
	
	/**
	 * 提取匹配的全部内容，此处不显示捕获块的功用
	 * @param cnt
	 * @param reg
	 * @return
	 * @throws IOException
	 */
    public static List<String> extractCnt(String cnt, String reg) throws IOException{

    	List<String> pcks= new ArrayList<String>();
		Pattern p = Pattern.compile(reg);
		Matcher matP = p.matcher(cnt);
		if(!matP.find()){
			//System.out.println(matP.group());;
			pcks.add(matP.group());
		}
		return pcks.size()==0?null:pcks;
	}
	
}
