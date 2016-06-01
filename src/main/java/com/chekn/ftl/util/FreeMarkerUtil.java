package com.chekn.ftl.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerUtil {
	public static Configuration createConfig(String basePackagePath) {
		Configuration config = new Configuration(Configuration.VERSION_2_3_23);
		config.setDefaultEncoding("UTF-8");
		config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		config.setClassForTemplateLoading(FreeMarkerUtil.class, basePackagePath);
		return config;
	}
	
	public static Configuration createConfig(File dirPath) throws IOException {
		Configuration config = new Configuration(Configuration.VERSION_2_3_23);
		config.setDefaultEncoding("UTF-8");
		config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		config.setDirectoryForTemplateLoading(dirPath);
		return config;
	}

	public static Template getTemplate(Configuration config, String ftl) {
		try {
			Template template = config.getTemplate(ftl);
			return template;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String generateByTemplate(Template template, Map<String, Object> params) {
		StringWriter writer = new StringWriter();
		try {
			template.process(params, writer);
		} catch (TemplateException | IOException e) {
			throw new RuntimeException(e);
		}
		return writer.toString();
	}
}
