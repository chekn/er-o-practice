package com.ckn.practice.book;

import java.util.List;

/**
 * Freemarker 自由选择格式输出
 * @author Pactera-NEN
 * @date 2016年7月5日-上午10:38:49
 */
public class FtlOutput implements Output {
	
	/**
	 * 
	 */
	@Override
	public String getOutputName() {
		// TODO Auto-generated method stub
		return "freeFmt.ftl.render";
	}
	
	
	/**
	 * 
	 */
	@Override
	public void opToDisk(List<Chapter> chapters) throws Exception {
		
	}

	
	
}
