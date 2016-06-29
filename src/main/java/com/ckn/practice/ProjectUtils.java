package com.ckn.practice;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年6月29日-下午4:38:48
 */
public class ProjectUtils {
	
	private static String classPath=ProjectUtils.class.getClassLoader().getResource("").getPath().toString().substring(1);
	
	public static String getSrcMainJavaPath() {
		String sourcePath=classPath+"../../src/main/java/";
		return sourcePath;
	}

}
