package com.ifre.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecgframework.codegenerate.generate.ICallBack;
import org.jeecgframework.codegenerate.pojo.Columnt;
import org.jeecgframework.codegenerate.util.CodeDateUtils;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;

import com.ifre.entity.brms.BizObjEntity;
import com.ifre.entity.brms.ObjPropEntity;
import com.ifre.entity.brms.RulePckgEntity;

public class GenEntityUtil implements ICallBack {
	private BizObjEntity entity;
	private RulePckgEntity pckg;

	private static final Log log = LogFactory.getLog(GenEntityUtil.class);
	private List<ObjPropEntity> objProperties;

	public GenEntityUtil(BizObjEntity entity, RulePckgEntity pckg, List<ObjPropEntity> objProperties) {
		this.entity = entity;
		this.pckg = pckg;
		this.objProperties = objProperties;
	}

	public Map execute() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("bussiPackage", pckg.getAllName().substring(0, pckg.getAllName().lastIndexOf(".")));
		data.put("entityPackage", pckg.getName());
		data.put("entityName", entity.getName());
		data.put("ftl_description", entity.getDescp());
		data.put("ftl_create_time", CodeDateUtils.dateToString(new Date()));
		try {
			List<Columnt> originalColumns = new ArrayList<Columnt>();
			for (ObjPropEntity objProp : objProperties) {
				Columnt columnt = new Columnt();
				columnt.setFieldName(objProp.getPropCode());
				columnt.setFieldType(objProp.getType());
				columnt.setFiledComment(objProp.getDescp());
				columnt.setOptionType(objProp.getIsList());
				originalColumns.add(columnt);
			}
			data.put("originalColumns", originalColumns);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public String generateToFile() {
		log.info((new StringBuilder("----Entity---Code----Generation----[")).append(entity.getName())
				.append("]------- 生成中...").toString());
		File file = null;
		InputStreamReader inputReader = null;
		BufferedReader bufferReader = null;
		StringBuffer strBuffer = new StringBuffer();
		try {
			GenEntityCodeFactory codeFactory = new GenEntityCodeFactory();
			codeFactory.setCallBack(this);
			codeFactory.invoke("entityBeanTemplate.ftl", "entity");
	
			String line = null;
			String fileNamePath = codeFactory.getCodePath("entity", pckg.getAllName().substring(0, pckg.getAllName().lastIndexOf(".")), pckg.getName(), entity.getName());
			log.info(fileNamePath);
			file = new File(fileNamePath);
			InputStream inputStream = new FileInputStream(file);
			inputReader = new InputStreamReader(inputStream, "utf-8");
			bufferReader = new BufferedReader(inputReader);
			while ((line = bufferReader.readLine()) != null) {
				strBuffer.append(line);
				strBuffer.append("\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (bufferReader != null)
					bufferReader.close();
				if (inputReader != null)
					inputReader.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			if(file !=null) file.delete();
		}

		log.info((new StringBuilder("----Entity----Code----Generation-----[")).append(entity.getName())
				.append("]------ 生成完成").toString());
		log.info(strBuffer.toString());
		return strBuffer.toString();
	}
	
	public String generateToFile(String templateName) {
		log.info((new StringBuilder("----Entity---Code----Generation----[")).append(entity.getName())
				.append("]------- 生成中...").toString());
		File file = null;
		InputStreamReader inputReader = null;
		BufferedReader bufferReader = null;
		StringBuffer strBuffer = new StringBuffer();
		try {
			GenEntityCodeFactory codeFactory = new GenEntityCodeFactory();
			codeFactory.setCallBack(this);
			codeFactory.invoke(templateName+".ftl", "entity");
	
			String line = null;
			String fileNamePath = codeFactory.getCodePath("entity", pckg.getAllName().substring(0, pckg.getAllName().lastIndexOf(".")), pckg.getName(), entity.getName());
			log.info(fileNamePath);
			file = new File(fileNamePath);
			InputStream inputStream = new FileInputStream(file);
			inputReader = new InputStreamReader(inputStream, "utf-8");
			bufferReader = new BufferedReader(inputReader);
			while ((line = bufferReader.readLine()) != null) {
				strBuffer.append(line);
				strBuffer.append("\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (bufferReader != null)
					bufferReader.close();
				if (inputReader != null)
					inputReader.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			if(file !=null) file.delete();
		}

		log.info((new StringBuilder("----Entity----Code----Generation-----[")).append(entity.getName())
				.append("]------ 生成完成").toString());
		log.info(strBuffer.toString());
		return strBuffer.toString();
	}

	/**
	 * 把输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	public String initcap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return this.getCamelStr(new String(ch));
	}

	// 例：user_name --> userName
	private String getCamelStr(String s) {
		while (s.indexOf("_") > 0) {
			int index = s.indexOf("_");
			// System.out.println(s.substring(index+1, index+2).toUpperCase());
			s = s.substring(0, index) + s.substring(index + 1, index + 2).toUpperCase() + s.substring(index + 2);
		}
		return s;
	}

	/**
	 * 创建.java文件所在路径 和 返回.java文件File对象
	 * 
	 * @param outDirFile
	 *            生成文件路径
	 * @param javaPackage
	 *            java包名
	 * @param javaClassName
	 *            java类名
	 * @return
	 */
	public static File toJavaFilename(File outDirFile, String javaPackage, String javaClassName) {
		String packageSubPath = javaPackage.replace('.', '/');
		File packagePath = new File(outDirFile, packageSubPath);
		File file = new File(packagePath, javaClassName + ".java");
		if (!packagePath.exists()) {
			packagePath.mkdirs();
		}
		return file;
	}
}
