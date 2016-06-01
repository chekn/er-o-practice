package com.ckn.practice;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年3月28日-下午7:10:40
 */
public class UpdateFreemarker {
	
	private static void test() throws Exception{
		Configuration cfg=new Configuration(Configuration.VERSION_2_3_23);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setClassForTemplateLoading(UpdateFreemarker.class, "/"+UpdateFreemarker.class.getPackage().getName().replace(".", "/"));
		
		Template tpt=cfg.getTemplate("test.ftl");
		
		List<Date> dates=new ArrayList<Date>();
		Date currYearFirstDay = DateUtils.truncate(new Date(), Calendar.getInstance().YEAR);
		for(int i=0;i<12;i++){
			dates.add(DateUtils.addMonths(currYearFirstDay, i));
		}
		StringWriter sw=new StringWriter();
		tpt.process(Collections.singletonMap("dates", dates), sw);
		
		System.out.print(sw.toString());
	}
	
	public static void main(String[] args){
		try {
			test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
