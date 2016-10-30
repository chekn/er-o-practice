package com.ckn.practice;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class CopyOfLc {

	public static void main(String[] args) throws FileNotFoundException,
			Exception {
		String htmlFile = "C:\\Users\\CHEKN\\Desktop\\a.htm";

		String pdfFile = "C:\\Users\\chekn\\Desktop\\a.htm.pdf";
		// PdfUtils.parseHTML2PDFFile(pdfFile, new FileInputStream(htmlFile));
		String ss = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(htmlFile), "UTF-8"));
		String t = "";
		while ((t = br.readLine()) != null) {
			// System.out.println(t);
			ss += t;
		}
		parseHTML2PDFFile2(pdfFile, ss);
	}

	public static void parseHTML2PDFFile2(String pdfFile, String html)
			throws DocumentException, IOException {
		Rectangle rect = new Rectangle(3066,768);  
		Document document = new Document(rect);
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(pdfFile));
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(writer, document,
				new ByteArrayInputStream(html.getBytes("Utf-8")),
				Charset.forName("UTF-8"));
		document.close();
	}
}
