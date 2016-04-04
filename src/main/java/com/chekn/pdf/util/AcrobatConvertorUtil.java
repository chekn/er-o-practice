package com.chekn.pdf.util;

import java.io.File;
import java.io.IOException;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;

public final class AcrobatConvertorUtil {
	private AcrobatConvertorUtil() {

	}

	public static synchronized void convertPdf2Html(String pdfFile, String htmlFile) {
		try {
			//文件加密解密环节
			if(PdfDecrypt.isEncrypted(pdfFile))
				PDFUnlock.unlockFile(pdfFile);
			
			ComThread.InitSTA();
			ActiveXComponent srcDoc = new ActiveXComponent("AcroExch.PDDoc");
			Dispatch.call(srcDoc, "Open", pdfFile);
			Dispatch jsObj = Dispatch.call(srcDoc, "GetJSObject").toDispatch();
			Dispatch.call(jsObj, "saveAs", htmlFile, "com.adobe.acrobat.html");
			Dispatch.call(srcDoc, "Close");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ComThread.Release();
		}
	}

	public static synchronized void killAcrobat() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM Acrobat.exe");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
