package com.ckn.practice.book;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class JsoupIndexGet implements IndexGet {

	public abstract String getIndexPageUrl();
	
	public abstract List<Index> queryIndexsInDoc(Document doc);
	
	@Override
	public List<Index> queryBookIndexs() throws IOException {
		Document doc=Jsoup.connect(this.getIndexPageUrl()).get();
		return this.queryIndexsInDoc(doc);
	}

}
