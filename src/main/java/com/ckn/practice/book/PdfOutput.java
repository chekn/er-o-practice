package com.ckn.practice.book;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.chekn.project.util.ProjectUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfOutput implements Output {

	private String fileNameWithExt="cm.pdf";
	
	@Override
	public String getOutputName() {
		return "pdf";
	}

	@Override
	public void opToDisk(List<Chapter> chapters) throws Exception {
		Document doc=new Document(PageSize.A4);;
		
		//step2
		PdfWriter.getInstance(doc, new FileOutputStream(ProjectUtils.getSrcMainJavaPath()+fileNameWithExt));
		doc.addTitle("");
		doc.addAuthor("");
		doc.addSubject("");
		doc.addKeywords("");
		
		//step3
		doc.open();
		//step4
		BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,  "UniGB-UCS2-H" ,  false );
		//Font fontChinese =  new  Font(bfChinese,12,Font.NORMAL,Color.GREEN);  
		Font fontChinese =  new  Font(bfChinese, 12 , Font.NORMAL, BaseColor.BLACK);   
		doc.add(new Paragraph("",fontChinese));
		
		//step5
		doc.close();
	}

}
