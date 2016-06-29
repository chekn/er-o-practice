package com.ckn.practice.book;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * 
 * @author Pactera-NEN
 * @date 2016年6月29日-下午4:17:34
 */
public class BookGet {
	private Logger logger =Logger.getLogger(this.getClass());
	
	private IndexGet indexGet;
	private ChapterGet chapterGet;
	private List<Output> outputs;
	
	/**
	 * @param indexGet
	 * @param chapterGet
	 * @param outputs
	 */
	public BookGet(IndexGet indexGet, ChapterGet chapterGet, List<Output> outputs) {
		super();
		this.indexGet = indexGet;
		this.chapterGet = chapterGet;
		this.outputs = outputs;
	}
	
	public void go() throws Exception {
		logger.info("1、getIndexs ");
		List<Index> indexs= indexGet.queryBookIndexs();
		
		logger.info("2、getChapters ");
		List<Chapter> chapters=new ArrayList<Chapter>();
		for(Index index:indexs) 
			chapters.add(chapterGet.queryChapter(index));
		
		logger.info("3、output ");
		for(Output output:outputs) {
			logger.info("outputInstance: "+output.getOutputName());
			output.opToDisk(chapters);
		}
	}
	
}
