package com.chekn.pdf.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

public class PDFUnlock {
	
	public static PdfReader unlockPdf(PdfReader paramPdfReader) {
		if (paramPdfReader == null)
			return paramPdfReader;
		try {
			Field localField = paramPdfReader.getClass().getDeclaredField("encrypted");
			localField.setAccessible(true);
			localField.set(paramPdfReader, Boolean.valueOf(false));
			return paramPdfReader;
		} catch (Exception localException) {
		}
		return paramPdfReader;
	}

	protected static Void copyWayDecrypt(String srcPath, String destPath) {
		try {
			PdfReader localPdfReader = unlockPdf(new PdfReader(srcPath));
			Document localDocument = new Document();
			PdfCopy localPdfCopy = new PdfCopy(localDocument, new FileOutputStream(destPath));
			localDocument.open();
			for (int i = 1;; i++) {
				if (i > localPdfReader.getNumberOfPages()) {
					localDocument.close();
					break;
				}
				localPdfCopy.addPage(localPdfCopy.getImportedPage(localPdfReader, i));
			}
			localPdfReader.close();
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		} catch (DocumentException localDocumentException) {
			localDocumentException.printStackTrace();
		}
		return null;
	}
	
    public static boolean isEncrypted(String filename) throws IOException{
    	PdfReader reader=new PdfReader(filename);
    	boolean isEncrypt=reader.isEncrypted();
    	reader.close();
    	return isEncrypt;
    }
	
	public static void unlockFile(String srcFp) throws IOException{
		//文件复制
    	File tmp=File.createTempFile("CheknCopyWayDecryptPdf-", ".pdf");
    	FileUtils.copyFile(new File(srcFp), tmp);
    	System.out.println("tmpFilePath:"+tmp.getPath());
    	
    	//写回原文件
    	copyWayDecrypt(tmp.getPath(),srcFp);
    	
    	//删临时文件
    	tmp.deleteOnExit();
	}
	
	public static void main(String[] args){
		String srcPth="C:\\Users\\Pactera-NEN\\Desktop\\1201714435.PDF";
		String destPath="C:\\Users\\Pactera-NEN\\Desktop\\unlock_1201714435.PDF";
		copyWayDecrypt(srcPth,destPath);
		System.out.println("文件解密完成");
	}

}
