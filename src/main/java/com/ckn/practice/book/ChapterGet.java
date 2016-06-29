package com.ckn.practice.book;

import java.io.IOException;


/**
 * 
 * @author Pactera-NEN
 * @date 2016年6月29日-下午3:42:39
 */
public interface ChapterGet {
	
	public Chapter queryChapter(Index index) throws IOException;
	
	
}
