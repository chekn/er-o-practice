package com.ckn.practice.book;

import java.util.List;

import com.chekn.mem.util.FreezeObjectUtils;
import com.chekn.project.util.ProjectUtils;

public class MemOutput implements Output {

	private String fileNameWithExt="freezeObj.tmp";
	
	@Override
	public String getOutputName() {
		return "mem-object";
	}

	@Override
	public void opToDisk(List<Chapter> chapters) throws Exception {
		String srcMainJavaPath=ProjectUtils.getSrcMainJavaPath();
		FreezeObjectUtils.writeObject(srcMainJavaPath+fileNameWithExt, chapters);
	}

}
