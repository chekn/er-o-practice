package com.ckn.practice.book;

import java.util.List;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年6月29日-下午3:29:19
 */
public interface Output {
	
	public String getOutputName();
	
	public void opToDisk(List<Chapter> chapters) throws Exception;

}
