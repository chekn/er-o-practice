package com.chekn.tool;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年3月30日-上午10:06:06
 */
public class ExceptionTool {
	
	public static String getExceptionStactInfo(Throwable e){
		StringWriter sw=new StringWriter();
		PrintWriter pw=new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	
	public static String getExceptionLocateInfo(Throwable e) {
		StringBuilder strBu=new StringBuilder();
		
		StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement element : stackTrace) {  
            String className = element.getClassName();  
            int lineNumber = element.getLineNumber();  
            String fileName = element.getFileName();  
            String methodName = element.getMethodName();  
            strBu.append("fileName:" + fileName + " lineNumber:" + lineNumber + " className:" + className + " methodName" + methodName).append("\n");  
        }  
        
        return strBu.toString();
	}
	
}
