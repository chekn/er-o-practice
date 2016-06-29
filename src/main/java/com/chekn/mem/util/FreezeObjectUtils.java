package com.chekn.mem.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年6月29日-下午4:34:51
 */
public class FreezeObjectUtils {
	
	/**
	 * 将对象序列化到磁盘文件中
	 * 序列化主要依赖java.io.ObjectOutputStream类,该类对java.io.FileOutputStream进一步做了封装,
	    这里主要使用ObjectOutputStream类的writeObject()方法实现序列化功能
	 * @param o
	 * @throws Exception
	 */
	public static void writeObject(String freezeObjFilePath,Object o) throws Exception{
		File f=new File(freezeObjFilePath);
		if(f.exists())
			f.delete();
		
		FileOutputStream os=new FileOutputStream(f);
		
		//ObjectOutputStream 核心类
		ObjectOutputStream oos =new ObjectOutputStream(os);
		oos.writeObject(o);
		
		oos.close();
		os.close();
	}
	
	
	/**
	 * 反序列化，将磁盘文件转化为对象
	 * 反序列化主要依赖java.io.ObjectInputStream类,该类对java.io.InputStream进一步做了封装,
	    这里主要使用ObjectInputStream类的readObject()方法实现反序列化功能。
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static Object readObject(File f) throws Exception{
		InputStream is=new FileInputStream(f);
		
		//ObjectInputStream 核心类
		ObjectInputStream ois=new ObjectInputStream(is);
		Object ot=ois.readObject();
		
		ois.close();
		is.close();
		
		return ot;
	}
}
