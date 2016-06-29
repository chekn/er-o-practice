package com.ckn.practice.book;

import java.util.List;

public class PdfOutput implements Output {

	@Override
	public String getOutputName() {
		return "pdf";
	}

	@Override
	public void opToDisk(List<Chapter> chapters) {
		//
	}

}
