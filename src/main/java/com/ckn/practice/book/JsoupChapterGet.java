package com.ckn.practice.book;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class JsoupChapterGet implements ChapterGet {

	
	public abstract Chapter queryChapterInDoc(Document doc, String indexName);
	
	@Override
	public Chapter queryChapter(Index index) throws IOException {
		Document doc= Jsoup.connect(index.getIndexUrl()).get();
		return this.queryChapterInDoc(doc,index.getIndexName());
	}

}
