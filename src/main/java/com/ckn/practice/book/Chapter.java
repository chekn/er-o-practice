package com.ckn.practice.book;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年6月29日-下午3:39:41
 */
public class Chapter implements Serializable {
	
	private static final long serialVersionUID = 419238425662339109L;
	
	private String chapterName;
	private List<String> chapterLines;
	
	//getter and setter
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	
	public List<String> getChapterLines() {
		return chapterLines;
	}
	public void setChapterLines(List<String> chapterLines) {
		this.chapterLines = chapterLines;
	}
	
	@Override
	public String toString() {
		return "Chapter [chapterName=" + chapterName + ", chapterLines="
				+ chapterLines + "]";
	}
	
	
	
}
