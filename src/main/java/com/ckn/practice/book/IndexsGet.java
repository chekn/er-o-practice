package com.ckn.practice.book;

import java.io.IOException;
import java.util.List;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年6月29日-下午3:34:25
 */
public interface IndexsGet {
	
	public List<Index> queryBookIndexs() throws IOException;
	
}
