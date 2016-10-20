package com.ifre.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.codegenerate.generate.BaseCodeFactory;
import org.jeecgframework.codegenerate.generate.ICallBack;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class GenEntityCodeFactory extends BaseCodeFactory {
	private String projectPath;
	private ICallBack callBack;

	public void generateFile(String templateFileName, String type, Map data) throws TemplateException, IOException {
		try {
			String entityPackage = data.get("entityPackage").toString();
			String entityName = data.get("entityName").toString();
			String fileNamePath = getCodePath(type, data.get("bussiPackage").toString(), entityPackage, entityName);
			String fileDir = StringUtils.substringBeforeLast(fileNamePath, File.separator);
			Configuration  cfg = new Configuration();  
		
			String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
			cfg.setDirectoryForTemplateLoading(new File(path+"jeecg"+File.separator+"template"));
			cfg.setDefaultEncoding("utf-8");
			Template template =  cfg.getTemplate(templateFileName);  
			
			//Template template = getConfiguration().getTemplate(templateFileName);
			FileUtils.forceMkdir(new File((new StringBuilder(String.valueOf(fileDir))).append(File.separator).toString()));
			Writer out = new OutputStreamWriter(new FileOutputStream(fileNamePath), "utf-8");
			
			template.process(data, out);
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public String getClassPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("./").getPath();
		return path;
	}

	public String getTemplatePath() {
		String path = (new StringBuilder(String.valueOf(getClassPath()))).append(CodeResourceUtil.TEMPLATEPATH)
				.toString();
		return path;
	}

	public String getCodePath(String type, String bussiPackage, String entityPackage, String entityName) {
		String path = getProjectPath();
		//if (path ==null) path = CodeResourceUtil.getProject_path();
		if(path == null) path = System.getProperty("saas_brms");
		bussiPackage = bussiPackage.replace(".", "/");
		StringBuilder str = new StringBuilder();
		if (StringUtils.isNotBlank(type)) {
			str.append(path);
			str.append(bussiPackage);
			str.append(File.separator);
			str.append(StringUtils.lowerCase(entityPackage));
			str.append(File.separator);
			str.append(StringUtils.capitalize(entityName));
			str.append(".java");
		}
		return str.toString();
	}

	public void invoke(String templateFileName, String type) throws TemplateException, IOException {
		Map data = new HashMap();
		data = callBack.execute();
		generateFile(templateFileName, type, data);
	}

	public ICallBack getCallBack() {
		return callBack;
	}

	public void setCallBack(ICallBack callBack) {
		this.callBack = callBack;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
}
