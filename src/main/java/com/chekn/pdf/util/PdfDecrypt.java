package com.chekn.pdf.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PdfDecrypt {
	
	private static String cp=PdfDecrypt.class.getClassLoader().getResource("").getPath().toString().substring(1);
	private static String srcPath=cp+"../../src/main/java/";
	
    public static void main(String[] args) throws Exception {
        /*PdfEncryptor.encrypt(new PdfReader("文件路径/CAM-040208.pdf"),
                new FileOutputStream("文件路径/Encrypted2.pdf"), 
                "用户密码".getBytes(), "所有者密码".getBytes(),
                PdfWriter.AllowDegradedPrinting, PdfWriter.STRENGTH128BITS);*/
 
        // 解密文件
        /*PdfReader reader = new PdfReader("文件路径/Encrypted2.pdf", "所有者密码".getBytes());
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("输出文件路径/Decrypted1.pdf"));
        stamper.close();*/
    	String filename="C:\\Users\\Pactera-NEN\\Desktop\\1200894952.PDF";
    	if(isEncrypted(filename))
    		unlockFile(filename,decrypt(filename));
    	
    	System.out.println(new PdfDecrypt().srcPath);
    	
    }
    
    
    public static boolean isEncrypted(String filename) throws IOException{
    	PdfReader reader=new PdfReader(filename);
    	boolean isEncrypt=reader.isEncrypted();
    	reader.close();
    	return isEncrypt;
    }
    
    public static String decrypt(String filename) throws Exception {
    	for(int i=0;i<Integer.MAX_VALUE;i++){
    		String currtUnit=Integer.toString(i);

    		if(i-60000==0) break;
    		if(i%1000==0) System.out.println("当前尝试到:"+i);
    		
    		PdfReader reader=null;
    		try{
    			reader=new PdfReader(filename,currtUnit.getBytes());
    		}catch(Exception e){
    			if(e instanceof BadPasswordException) continue;
    			else throw e;
    		}finally{
    			if(reader!=null) reader.close(); 
    		}

    		FileUtils.write(new File(srcPath+"/key.txt"), i+"\r\n", true);
    		System.out.println("密码:"+i);
    		return currtUnit;
    	} 

    	throw new RuntimeException("可用密码已经用完,没找到密码");
    }
    
    public static void stamper(String srcFp, String pwd,String tarFp) throws IOException, DocumentException{
    	PdfReader pdfReader=new PdfReader(srcFp,pwd.getBytes());
    	PdfStamper stamper=new PdfStamper(pdfReader,new FileOutputStream(tarFp));
    	stamper.close();
    	pdfReader.close();
    }
    
    public static void unlockFile(String srcFp, String pwd) throws IOException, DocumentException{
    	//文件复制
    	File tmp=File.createTempFile("CheknDecryptPdf-", ".pdf");
    	FileUtils.copyFile(new File(srcFp), tmp);
    	System.out.println("tmpFilePath:"+tmp.getPath());
    	
    	//压回原文件
    	stamper(tmp.getPath(), pwd, srcFp);
    	
    	//删临时文件
    	tmp.deleteOnExit();
    }
}